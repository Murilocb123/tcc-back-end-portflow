package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import br.com.murilocb123.portflow.dto.AssetFilterDTO;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.repositories.AssetRepository;
import br.com.murilocb123.portflow.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    @Override
    public AssetEntity create(AssetEntity entity) {
        return assetRepository.save(entity);
    }

    @Override
    public AssetEntity getById(UUID id) {
        return assetRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Asset not found"));
    }

    @Override
    public AssetEntity update(UUID id, AssetEntity entity) {
        AssetEntity existing = getById(id);
        entity.setId(existing.getId());
        return assetRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        assetRepository.deleteById(id);
    }

    @Override
    public Page<AssetEntity> list(Pageable pageable, AssetFilterDTO filter) {
        if (filter != null && filter.name() != null) {
            return assetRepository.findAllByNameContainsIgnoreCase(filter.name(), pageable);
        }
        return assetRepository.findAll(pageable);
    }

    @Override
    public List<AssetEntity> listAll() {
        return assetRepository.findAll();
    }

    @Override
    public List<AssetEntity> listAllByAssetsAvailable() {
        return assetRepository.findAllByAssetsIdAvailable(AppContextHolder.getCurrentPortfolio());
    }
}

