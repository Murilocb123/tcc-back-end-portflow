package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.*;
import br.com.murilocb123.portflow.domain.enums.TxnType;
import br.com.murilocb123.portflow.dto.StrategyDTO;
import br.com.murilocb123.portflow.infra.exceptions.custom.BusinessException;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.mapper.AssetMapper;
import br.com.murilocb123.portflow.mapper.BrokerMapper;
import br.com.murilocb123.portflow.repositories.AssetHistoryRepository;
import br.com.murilocb123.portflow.repositories.EventRepository;
import br.com.murilocb123.portflow.repositories.PortfolioAssetRepository;
import br.com.murilocb123.portflow.repositories.TransactionRepository;
import br.com.murilocb123.portflow.service.PortfolioAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioAssetServiceImpl implements PortfolioAssetService {
    private final PortfolioAssetRepository portfolioAssetRepository;
    private final TransactionRepository transactionRepository;
    private final EventRepository eventRepository;
    private final AssetHistoryRepository assetHistoryRepository;

    @Override
    public PortfolioAssetEntity create(PortfolioAssetEntity entity) {
        return portfolioAssetRepository.save(entity);
    }

    @Override
    public PortfolioAssetEntity getById(UUID id) {
        return portfolioAssetRepository.findById(id).orElseThrow(() -> new NoSuchElementException("PortfolioAsset not found"));
    }

    @Override
    public PortfolioAssetEntity update(UUID id, PortfolioAssetEntity entity) {
        PortfolioAssetEntity existing = getById(id);
        entity.setId(existing.getId());
        return portfolioAssetRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        portfolioAssetRepository.deleteById(id);
    }

    @Override
    public Page<PortfolioAssetEntity> list(Pageable pageable) {
        return portfolioAssetRepository.findAllByPortfolioId(AppContextHolder.getCurrentPortfolio(), pageable);
    }

    @Override
    public Page<StrategyDTO> listWithCurrentPrice(Pageable pageable) {
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setId(AppContextHolder.getCurrentPortfolio());
        var page = portfolioAssetRepository.findAllByPortfolioId(AppContextHolder.getCurrentPortfolio(), pageable);
        var assetId = page.stream().map(pa -> pa.getAsset().getId()).toList();
        var lastPrices = assetHistoryRepository.findLastQuoteByAssetIdIn(assetId);
        // convert para strategyDTO
        var strategyList = page.stream().map(pa -> {
            var lastPrice = lastPrices.stream()
                    .filter(lp -> lp.getAsset().getId().equals(pa.getAsset().getId()))
                    .findFirst()
                    .map(AssetHistoryEntity::getClosePrice)
                    .orElse(BigDecimal.ZERO);
            return new StrategyDTO(
                    pa.getId(),
                    pa.getQuantity(),
                    pa.getAveragePrice(),
                    pa.getTotalInvested(),
                    pa.getTotalFee(),
                    pa.getTotalTax(),
                    pa.getTotalReceivable(),
                    BrokerMapper.toDTO(pa.getBroker()),
                    AssetMapper.toDTO(pa.getAsset()),
                    pa.getStartDate(),
                    lastPrice
            );
        }).toList();
        return new PageImpl<>(strategyList, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void persistPortfolio(PortfolioAssetEntity portfolioAssetEntity) {
        var portfolio = new PortfolioEntity();
        portfolio.setId(AppContextHolder.getCurrentPortfolio());
        portfolioAssetEntity.setPortfolio(portfolio);
        portfolioAssetEntity.setTotalReceivable(BigDecimal.ZERO);
        portfolioAssetEntity.setTotalInvested(portfolioAssetEntity.getAveragePrice().multiply(portfolioAssetEntity.getQuantity()));
        portfolioAssetEntity.setTotalFee(BigDecimal.ZERO);
        portfolioAssetEntity.setTotalTax(BigDecimal.ZERO);
        var opt = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(
                portfolioAssetEntity.getPortfolio().getId(),
                portfolioAssetEntity.getAsset().getId(),
                portfolioAssetEntity.getBroker().getId());
        if (opt.isPresent()) {
            throw new BusinessException("Ativo ja existe na carteira", "O ativo informado ja existe na carteira para a corretora selecionada.");
        }
        var transactionEntity = new TransactionEntity();
        transactionEntity.setTradeDate(portfolioAssetEntity.getStartDate());
        transactionEntity.setType(TxnType.BUY);
        transactionEntity.setBroker(portfolioAssetEntity.getBroker());
        transactionEntity.setAsset(portfolioAssetEntity.getAsset());
        transactionEntity.setQuantity(portfolioAssetEntity.getQuantity());
        transactionEntity.setPrice(portfolioAssetEntity.getAveragePrice());
        transactionEntity.setGrossValue(portfolioAssetEntity.getAveragePrice().multiply(portfolioAssetEntity.getQuantity()));
        transactionEntity.setNetValue(portfolioAssetEntity.getAveragePrice().multiply(portfolioAssetEntity.getQuantity()));
        transactionEntity.setFeeValue(BigDecimal.ZERO);
        transactionEntity.setTaxValue(BigDecimal.ZERO);
        transactionEntity.setPortfolio(portfolioAssetEntity.getPortfolio());
        transactionRepository.save(transactionEntity);
        portfolioAssetRepository.save(portfolioAssetEntity);
    }


    @Override
    @Transactional
    public void updateOrDeleteByTransactionDelete(TransactionEntity transactionEntity, boolean isDelete) {
        var opt = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(
                transactionEntity.getPortfolio().getId(),
                transactionEntity.getAsset().getId(),
                transactionEntity.getBroker().getId());

        opt.ifPresent(portfolioAssetEntity -> {
            revertTransaction(portfolioAssetEntity, transactionEntity);
            if (portfolioAssetEntity.getQuantity().compareTo(BigDecimal.ZERO) == 0 && isDelete) {
                portfolioAssetRepository.deleteById(portfolioAssetEntity.getId());
                deleteAllEventsAndTransactions(transactionEntity.getPortfolio().getId(), transactionEntity.getAsset().getId(), transactionEntity.getBroker().getId());
                return;
            }
            portfolioAssetRepository.save(portfolioAssetEntity);
        });
    }


    @Override
    @Transactional
    public void createOrUpdateByTransactionCreate(TransactionEntity transactionEntity) {
        var opt = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(
                transactionEntity.getPortfolio().getId(),
                transactionEntity.getAsset().getId(),
                transactionEntity.getBroker().getId());

        opt.ifPresentOrElse(
                portfolioAssetEntity -> {
                    applyTransaction(portfolioAssetEntity, transactionEntity);
                    portfolioAssetRepository.save(portfolioAssetEntity);
                },
                () -> {
                    var novo = new PortfolioAssetEntity();
                    novo.setPortfolio(transactionEntity.getPortfolio());
                    novo.setAsset(transactionEntity.getAsset());
                    novo.setBroker(transactionEntity.getBroker());
                    applyTransaction(novo, transactionEntity);
                    portfolioAssetRepository.save(novo);
                }
        );
    }

    @Override
    @Transactional
    public void updateOrDeleteByEventDelete(EventEntity eventEntity) {
        var port = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(
                eventEntity.getPortfolio().getId(),
                eventEntity.getAsset().getId(),
                eventEntity.getBroker().getId());
        port.ifPresent(portfolioAssetEntity -> {
            portfolioAssetEntity.setTotalReceivable(
                    portfolioAssetEntity.getTotalReceivable().subtract(eventEntity.getTotalValue())
            );
            portfolioAssetRepository.save(portfolioAssetEntity);
        });
    }

    @Override
    @Transactional
    public void createOrUpdateByEventCreate(EventEntity eventEntity) {
        var opt = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(
                eventEntity.getPortfolio().getId(),
                eventEntity.getAsset().getId(),
                eventEntity.getBroker().getId());

        opt.ifPresent(
                portfolioAssetEntity -> {
                    portfolioAssetEntity.setTotalReceivable(
                            portfolioAssetEntity.getTotalReceivable().add(eventEntity.getTotalValue())
                    );
                    portfolioAssetRepository.save(portfolioAssetEntity);
                }
        );
    }

    private void applyTransaction(PortfolioAssetEntity asset, TransactionEntity txn) {
        if (asset.getStartDate() == null || txn.getTradeDate().isBefore(asset.getStartDate())) {
            asset.setStartDate(txn.getTradeDate());
        }
        if (Objects.equals(TxnType.BUY, txn.getType())) {
            BigDecimal totalInvest = asset.getTotalInvested().add(txn.getNetValue());
            BigDecimal newQuantity = asset.getQuantity().add(txn.getQuantity());
            asset.setAveragePrice(newQuantity.compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : totalInvest.divide(newQuantity, RoundingMode.HALF_UP));
            asset.setQuantity(newQuantity);
        } else if (Objects.equals(TxnType.SELL, txn.getType())) {
            asset.setQuantity(asset.getQuantity().subtract(txn.getQuantity()));
            // O preço médio não muda em venda
        }
        asset.setTotalFee(asset.getTotalFee().add(txn.getFeeValue()));
        asset.setTotalTax(asset.getTotalTax().add(txn.getTaxValue()));
        asset.setTotalInvested(asset.getAveragePrice().multiply(asset.getQuantity()));
        validateFinalValues(asset);
    }

    private void revertTransaction(PortfolioAssetEntity asset, TransactionEntity txn) {
        if (Objects.equals(TxnType.BUY, txn.getType())) {
            var totalInvest = asset.getTotalInvested().subtract(txn.getNetValue());
            var newQuantity = asset.getQuantity().subtract(txn.getQuantity());
            asset.setAveragePrice(newQuantity.compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : totalInvest.divide(newQuantity, RoundingMode.HALF_UP));
            asset.setQuantity(newQuantity);
        } else if (Objects.equals(TxnType.SELL, txn.getType())) {
            asset.setQuantity(asset.getQuantity().add(txn.getQuantity()));
        }
        asset.setTotalFee(asset.getTotalFee().subtract(txn.getFeeValue()));
        asset.setTotalTax(asset.getTotalTax().subtract(txn.getTaxValue()));
        asset.setTotalInvested(asset.getAveragePrice().multiply(asset.getQuantity()));
        validateFinalValues(asset);
    }

    private void validateFinalValues(PortfolioAssetEntity portfolioAssetEntity) {
        if (portfolioAssetEntity.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Quantidade invalida", "A quantidade do ativo na carteira para a corretora selecionada não pode ser negativa");
        }
    }

    private void deleteAllEventsAndTransactions(UUID portfolioId, UUID assetId, UUID brokerId) {
        eventRepository.deleteAllByPortfolioIdAndAssetIdAndBrokerId(portfolioId, assetId, brokerId);
        transactionRepository.deleteAllByPortfolioIdAndAssetIdAndBrokerId(portfolioId, assetId, brokerId);
    }

}

