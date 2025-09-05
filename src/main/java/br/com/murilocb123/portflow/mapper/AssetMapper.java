package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import br.com.murilocb123.portflow.dto.AssetDTO;

public class AssetMapper {
    public static AssetDTO toDTO(AssetEntity entity) {
        if (entity == null) return null;
        return new AssetDTO(
            entity.getId(),
            entity.getTicker(),
            entity.getName(),
            entity.getType(),
            entity.getCurrency(),
            entity.getExchange()
        );
    }

    public static AssetEntity toEntity(AssetDTO dto) {
        if (dto == null) return null;
        AssetEntity entity = new AssetEntity();
        entity.setId(dto.id());
        entity.setTicker(dto.ticker());
        entity.setName(dto.name());
        entity.setType(dto.type());
        entity.setCurrency(dto.currency());
        entity.setExchange(dto.exchange());
        return entity;
    }
}

