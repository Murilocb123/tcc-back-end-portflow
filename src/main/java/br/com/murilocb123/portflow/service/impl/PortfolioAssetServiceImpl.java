package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.repositories.PortfolioAssetRepository;
import br.com.murilocb123.portflow.service.PortfolioAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioAssetServiceImpl implements PortfolioAssetService {
    private final PortfolioAssetRepository portfolioAssetRepository;

    @Override
    public PortfolioAssetEntity create(PortfolioAssetEntity entity) {
        return portfolioAssetRepository.save(entity);
    }

    @Override
    public PortfolioAssetEntity getById(UUID id) {
        return portfolioAssetRepository.findById(id).orElseThrow(() -> new NoSuchElementException("PortfolioAsset not found"));
    }

    @Override
    public PortfolioAssetEntity update(UUID id, PortfolioAssetEntity entity) {
        PortfolioAssetEntity existing = getById(id);
        entity.setId(existing.getId());
        return portfolioAssetRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        portfolioAssetRepository.deleteById(id);
    }

    @Override
    public Page<PortfolioAssetEntity> list(Pageable pageable) {
        return portfolioAssetRepository.findAll(pageable);
    }
}

