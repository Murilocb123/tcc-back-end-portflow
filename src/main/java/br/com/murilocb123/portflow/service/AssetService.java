package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import br.com.murilocb123.portflow.dto.AssetFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AssetService {
    AssetEntity create(AssetEntity entity);
    AssetEntity getById(UUID id);
    AssetEntity update(UUID id, AssetEntity entity);
    void delete(UUID id);
    Page<AssetEntity> list(Pageable pageable, AssetFilterDTO filter);
    List<AssetEntity> listAll();
    List<AssetEntity> listAllByAssetsAvailable();
}

