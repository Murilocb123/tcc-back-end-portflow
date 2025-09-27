package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.domain.enums.TxnType;
import br.com.murilocb123.portflow.infra.exceptions.custom.BusinessException;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.repositories.PortfolioAssetRepository;
import br.com.murilocb123.portflow.repositories.TransactionRepository;
import br.com.murilocb123.portflow.service.PortfolioAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioAssetServiceImpl implements PortfolioAssetService {
    private final PortfolioAssetRepository portfolioAssetRepository;
    private final TransactionRepository transactionRepository;

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
    @Transactional
    public void updateOrDeleteByTransactionDelete(TransactionEntity transactionEntity) {
        var existing = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(transactionEntity.getPortfolio().getId(),
                transactionEntity.getAsset().getId(),
                transactionEntity.getBroker().getId());
        existing.ifPresent(portfolioAssetEntity -> {
            portfolioAssetEntity.setTotalFee(portfolioAssetEntity.getTotalFee().subtract(transactionEntity.getFeeValue()));
            portfolioAssetEntity.setTotalTax(portfolioAssetEntity.getTotalTax().subtract(transactionEntity.getTaxValue()));
            portfolioAssetEntity.setQuantity(portfolioAssetEntity.getQuantity().subtract(transactionEntity.getQuantity()));
            if (portfolioAssetEntity.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                portfolioAssetRepository.delete(portfolioAssetEntity);
            } else {
                portfolioAssetEntity.setAveragePrice(transactionRepository.calculateAveragePrice(
                        transactionEntity.getPortfolio().getId(),
                        transactionEntity.getAsset().getId(),
                        transactionEntity.getBroker().getId()
                ));
                portfolioAssetRepository.save(portfolioAssetEntity);
            }
        });
    }

    @Override
    @Transactional
    public void createOrUpdateByTransactionCreate(TransactionEntity transactionEntity, Boolean isUpdate) {
        var existing = portfolioAssetRepository.findByPortfolioIdAndAssetIdAndBrokerId(transactionEntity.getPortfolio().getId(),
                transactionEntity.getAsset().getId(),
                transactionEntity.getBroker().getId());
        existing.ifPresentOrElse(
                portfolioAssetEntity -> {
                    recalculatePortfolioAsset(transactionEntity, portfolioAssetEntity, isUpdate);
                    portfolioAssetRepository.save(portfolioAssetEntity);
                },
                () -> {
                    var newPortfolioAsset = new PortfolioAssetEntity();
                    newPortfolioAsset.setPortfolio(new PortfolioEntity());
                    newPortfolioAsset.getPortfolio().setId(transactionEntity.getPortfolio().getId());
                    newPortfolioAsset.setAsset(transactionEntity.getAsset());
                    newPortfolioAsset.setBroker(transactionEntity.getBroker());
                    recalculatePortfolioAsset(transactionEntity, newPortfolioAsset, isUpdate);
                    portfolioAssetRepository.save(newPortfolioAsset);
                }
        );
    }

    private void recalculatePortfolioAsset(TransactionEntity transactionEntity, PortfolioAssetEntity portfolioAssetEntity, boolean isUpdate) {
        var quantity = portfolioAssetEntity.getQuantity();
        var feeValue = portfolioAssetEntity.getTotalFee();
        var taxValue = portfolioAssetEntity.getTotalTax();
        if (Objects.equals(TxnType.SELL, transactionEntity.getType())) {
            quantity = quantity.subtract(transactionEntity.getQuantity());
        } else {
            quantity = quantity.add(transactionEntity.getQuantity());
        }
        if (isUpdate) {
            feeValue = feeValue.subtract(transactionEntity.getFeeValue());
            taxValue = taxValue.subtract(transactionEntity.getTaxValue());
        } else {
            feeValue = feeValue.add(transactionEntity.getFeeValue());
            taxValue = taxValue.add(transactionEntity.getTaxValue());
        }
        portfolioAssetEntity.setQuantity(quantity);
        portfolioAssetEntity.setTotalFee(feeValue);
        portfolioAssetEntity.setTotalTax(taxValue);
        portfolioAssetEntity.setAveragePrice(transactionRepository.calculateAveragePrice(
                transactionEntity.getPortfolio().getId(),
                transactionEntity.getAsset().getId(),
                transactionEntity.getBroker().getId()
        ));
        validateFinalValues(portfolioAssetEntity);
    }

    private void validateFinalValues(PortfolioAssetEntity portfolioAssetEntity) {
        if (portfolioAssetEntity.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Quantidade invalida", "A quantidade do ativo na carteira para a corretora selecionada não pode ser negativa");
        }
    }

}

