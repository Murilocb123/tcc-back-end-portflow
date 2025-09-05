package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.dto.PortfolioAssetDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PortfolioAssetMapper {
    public static PortfolioAssetDTO toDTO(PortfolioAssetEntity entity) {
        if (entity == null) return null;
        return new PortfolioAssetDTO(
            entity.getId(),
            entity.getQuantity(),
            entity.getAveragePrice(),
            entity.getTotalValue(),
            BrokerMapper.toDTO(entity.getBroker()),
            AssetMapper.toDTO(entity.getAsset())
        );
    }

    public static PortfolioAssetEntity toEntity(PortfolioAssetDTO dto) {
        if (dto == null) return null;
        PortfolioAssetEntity entity = new PortfolioAssetEntity();
        entity.setId(dto.id());
        entity.setQuantity(dto.quantity());
        entity.setAveragePrice(dto.averagePrice());
        entity.setTotalValue(dto.totalValue());
        entity.setBroker(BrokerMapper.toEntity(dto.broker()));
        entity.setAsset(AssetMapper.toEntity(dto.asset()));
        return entity;
    }
}

