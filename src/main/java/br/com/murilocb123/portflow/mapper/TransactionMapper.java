package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.domain.enums.TxnType;
import br.com.murilocb123.portflow.dto.TransactionDTO;

public class TransactionMapper {
    public static TransactionDTO toDTO(TransactionEntity entity) {
        if (entity == null) return null;
        return new TransactionDTO(
            entity.getId(),
            entity.getBroker() != null ? entity.getBroker().getId() : null,
            entity.getAsset() != null ? entity.getAsset().getId() : null,
            entity.getPortfolio() != null ? entity.getPortfolio().getId() : null,
            entity.getType() != null ? entity.getType() : null,
            entity.getValue(),
            entity.getTxnDate(),
            entity.getDescription()
        );
    }

    public static TransactionEntity toEntity(TransactionDTO dto) {
        if (dto == null) return null;
        TransactionEntity entity = new TransactionEntity();
        entity.setId(dto.id());
        entity.setValue(dto.value());
        entity.setTxnDate(dto.txnDate());
        entity.setDescription(dto.description());
        entity.setTxnType(dto.txnType() != null ? TxnType.valueOf(dto.txnType()) : null);
        // Broker, Asset e Portfolio devem ser setados via service/repository se necessário
        return entity;
    }
}

