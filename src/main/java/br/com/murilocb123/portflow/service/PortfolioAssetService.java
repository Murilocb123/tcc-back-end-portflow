package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.dto.StrategyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PortfolioAssetService {
    PortfolioAssetEntity create(PortfolioAssetEntity entity);
    PortfolioAssetEntity getById(UUID id);
    PortfolioAssetEntity update(UUID id, PortfolioAssetEntity entity);
    void delete(UUID id);
    Page<PortfolioAssetEntity> list(Pageable pageable);
    void updateOrDeleteByTransactionDelete(TransactionEntity transactionEntity, boolean isDelete);
    void createOrUpdateByTransactionCreate(TransactionEntity transactionEntity);
    void updateOrDeleteByEventDelete(EventEntity eventEntity);
    void createOrUpdateByEventCreate(EventEntity eventEntity);
    Page<StrategyDTO> listWithCurrentPrice(Pageable pageable);
    void persistPortfolio(PortfolioAssetEntity portfolioAssetEntity);
}

