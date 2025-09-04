package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import br.com.murilocb123.portflow.repositories.AssetHistoryRepository;
import br.com.murilocb123.portflow.service.AssetHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetHistoryServiceImpl implements AssetHistoryService {
    private final AssetHistoryRepository assetHistoryRepository;

    @Override
    public AssetHistoryEntity create(AssetHistoryEntity entity) {
        return assetHistoryRepository.save(entity);
    }

    @Override
    public AssetHistoryEntity getById(UUID assetId, String date) {
        AssetPriceId id = new AssetPriceId(assetId, LocalDate.parse(date));
        return assetHistoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("AssetHistory not found"));
    }

    @Override
    public AssetHistoryEntity update(UUID assetId, String date, AssetHistoryEntity entity) {
        AssetPriceId id = new AssetPriceId(assetId, LocalDate.parse(date));
        AssetHistoryEntity existing = getById(assetId, date);
        entity.setId(existing.getId());
        return assetHistoryRepository.save(entity);
    }

    @Override
    public void delete(UUID assetId, String date) {
        AssetPriceId id = new AssetPriceId(assetId, LocalDate.parse(date));
        assetHistoryRepository.deleteById(id);
    }

    @Override
    public Page<AssetHistoryEntity> list(Pageable pageable) {
        return assetHistoryRepository.findAll(pageable);
    }
}

