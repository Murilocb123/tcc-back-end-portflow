package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.dto.PortfolioAssetDTO;

public class PortfolioAssetMapper {
    public static PortfolioAssetDTO toDTO(PortfolioAssetEntity entity) {
        if (entity == null) return null;
        return new PortfolioAssetDTO(
            entity.getId(),
            entity.getAsset() != null ? entity.getAsset().getId() : null,
            entity.getPortfolio() != null ? entity.getPortfolio().getId() : null,
            entity.getBroker() != null ? entity.getBroker().getId() : null,
            entity.getQuantity(),
            entity.getAveragePrice(),
            entity.getTotalValue()
        );
    }

    public static PortfolioAssetEntity toEntity(PortfolioAssetDTO dto) {
        if (dto == null) return null;
        PortfolioAssetEntity entity = new PortfolioAssetEntity();
        entity.setId(dto.id());
        entity.setQuantity(dto.quantity());
        entity.setAveragePrice(dto.averagePrice());
        entity.setTotalValue(dto.totalValue());
        // Asset, Portfolio e Broker devem ser setados via service/repository se necessário
        return entity;
    }
}

