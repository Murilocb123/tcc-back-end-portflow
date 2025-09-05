package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.dto.TransactionDTO;
import br.com.murilocb123.portflow.dto.BrokerDTO;
import br.com.murilocb123.portflow.dto.AssetDTO;
import br.com.murilocb123.portflow.dto.PortfolioDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TransactionMapper {
    public static TransactionDTO toDTO(TransactionEntity entity) {
        if (entity == null) return null;
        return new TransactionDTO(
            entity.getId(),
            BrokerMapper.toDTO(entity.getBroker()),
            AssetMapper.toDTO(entity.getAsset()),
            PortfolioMapper.toDTO(entity.getPortfolio()),
            entity.getType(),
            entity.getTradeDate(),
            entity.getQuantity(),
            entity.getPrice(),
            entity.getGrossValue(),
            entity.getNetValue(),
            entity.getFeeValue(),
            entity.getTaxValue(),
            entity.getDescription()
        );
    }

    public static TransactionEntity toEntity(TransactionDTO dto) {
        if (dto == null) return null;
        TransactionEntity entity = new TransactionEntity();
        entity.setId(dto.id());
        entity.setBroker(BrokerMapper.toEntity(dto.broker()));
        entity.setAsset(AssetMapper.toEntity(dto.asset()));
        entity.setPortfolio(PortfolioMapper.toEntity(dto.portfolio()));
        entity.setType(dto.type());
        entity.setTradeDate(dto.tradeDate());
        entity.setQuantity(dto.quantity());
        entity.setPrice(dto.price());
        entity.setGrossValue(dto.grossValue());
        entity.setNetValue(dto.netValue());
        entity.setFeeValue(dto.feeValue());
        entity.setTaxValue(dto.taxValue());
        entity.setDescription(dto.description());
        return entity;
    }
}

