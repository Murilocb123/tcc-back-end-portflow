package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import br.com.murilocb123.portflow.dto.AssetHistoryDTO;

public class AssetHistoryMapper {
    public static AssetHistoryDTO toDTO(AssetHistoryEntity entity) {
        if (entity == null || entity.getId() == null) return null;
        return new AssetHistoryDTO(
            entity.getId().getAssetId(),
            entity.getId().getDate(),
            entity.getClosePrice()
        );
    }

    public static AssetHistoryEntity toEntity(AssetHistoryDTO dto) {
        if (dto == null) return null;
        AssetHistoryEntity entity = new AssetHistoryEntity();
        entity.setId(new AssetPriceId(dto.assetId(), dto.date()));
        entity.setClosePrice(dto.closePrice());
        return entity;
    }
}

