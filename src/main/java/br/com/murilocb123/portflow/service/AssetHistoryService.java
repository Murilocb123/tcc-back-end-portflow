package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssetHistoryService {
    AssetHistoryEntity create(AssetHistoryEntity entity);
    AssetHistoryEntity getById(UUID assetId, String date);
    AssetHistoryEntity update(UUID assetId, String date, AssetHistoryEntity entity);
    void delete(UUID assetId, String date);
    Page<AssetHistoryEntity> list(Pageable pageable);
}

