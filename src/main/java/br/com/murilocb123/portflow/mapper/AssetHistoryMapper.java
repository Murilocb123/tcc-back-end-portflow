package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import br.com.murilocb123.portflow.dto.AssetHistoryDTO;

public class AssetHistoryMapper {
    public static AssetHistoryDTO toDTO(AssetHistoryEntity entity) {
        if (entity == null) return null;
        AssetPriceId id = entity.getId();
        return new AssetHistoryDTO(
            id.getAssetId(),
            id.getPriceDate(),
            entity.getClosePrice(),
            entity.getOpenPrice(),
            entity.getHighPrice(),
            entity.getLowPrice(),
            entity.getVolume()
        );
    }

    public static AssetHistoryEntity toEntity(AssetHistoryDTO dto) {
        if (dto == null) return null;
        AssetHistoryEntity entity = new AssetHistoryEntity();
        AssetPriceId id = new AssetPriceId();
        id.setAssetId(dto.assetId());
        id.setPriceDate(dto.date());
        entity.setId(id);
        entity.setClosePrice(dto.closePrice());
        entity.setOpenPrice(dto.openPrice());
        entity.setHighPrice(dto.highPrice());
        entity.setLowPrice(dto.lowPrice());
        entity.setVolume(dto.volume());
        // O relacionamento AssetEntity deve ser setado externamente ou buscar pelo assetId
        return entity;
    }
}
