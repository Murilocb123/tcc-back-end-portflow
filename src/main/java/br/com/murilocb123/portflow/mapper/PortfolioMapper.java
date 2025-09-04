package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.dto.PortfolioDTO;

public class PortfolioMapper {
    public static PortfolioDTO toDTO(PortfolioEntity entity) {
        if (entity == null) return null;
        return new PortfolioDTO(
            entity.getId(),
            entity.getName(),
            entity.getDefaultPortfolio(),
            null // tenantId pode ser setado se necessário
        );
    }

    public static PortfolioEntity toEntity(PortfolioDTO dto) {
        if (dto == null) return null;
        PortfolioEntity entity = new PortfolioEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDefaultPortfolio(dto.defaultPortfolio());
        return entity;
    }
}

