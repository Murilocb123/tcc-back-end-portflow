package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.domain.enums.TxnType;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.repositories.TransactionRepository;
import br.com.murilocb123.portflow.service.PortfolioAssetService;
import br.com.murilocb123.portflow.service.TransactionService;
import br.com.murilocb123.portflow.utils.PortfolioUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    PortfolioAssetService portfolioAssetService;


    @Override
    @Transactional
    public TransactionEntity create(TransactionEntity entity) {
        calculateGrossAndNetValue(entity);
        PortfolioUtils.addPortfolioToEntity(entity);
        var entitySaved = transactionRepository.save(entity);
        portfolioAssetService.createOrUpdateByTransactionCreate(entitySaved);
        return entitySaved;
    }

    @Override
    public TransactionEntity getById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found"));
    }

    @Override
    @Transactional
    public TransactionEntity update(UUID id, TransactionEntity entity) {
        TransactionEntity existing = getById(id);
        entity.setId(existing.getId());
        calculateGrossAndNetValue(entity);
        PortfolioUtils.addPortfolioToEntity(entity);
        if (!Objects.equals(existing.getBroker().getId(), entity.getBroker().getId()) ||
                !Objects.equals(existing.getAsset().getId(), entity.getAsset().getId())) {
            portfolioAssetService.updateOrDeleteByTransactionDelete(existing);
        }
        var entitySaved = transactionRepository.save(entity);
        portfolioAssetService.createOrUpdateByTransactionCreate(entitySaved);
        return entitySaved;
    }

    @Override
    public void delete(UUID id) {
        var entity = getById(id);
        transactionRepository.deleteById(id);
        portfolioAssetService.updateOrDeleteByTransactionDelete(entity);
    }

    @Override
    public Page<TransactionEntity> list(Pageable pageable) {
        return transactionRepository.findAllByPortfolioId(AppContextHolder.getCurrentPortfolio(), pageable);
    }

    private void calculateGrossAndNetValue(TransactionEntity entity) {
        if (entity.getQuantity() != null && entity.getPrice() != null) {
            var grossValue = entity.getQuantity().multiply(entity.getPrice());
            entity.setGrossValue(grossValue);

            BigDecimal netValue;
            if (Objects.equals(TxnType.SELL, entity.getType())) {
                netValue = grossValue.subtract(entity.getFeeValue()).subtract(entity.getTaxValue());
            } else {
                netValue = grossValue.add(entity.getFeeValue()).add(entity.getTaxValue());
            }

            entity.setNetValue(netValue);
        }
    }
}
