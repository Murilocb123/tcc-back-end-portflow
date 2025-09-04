package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.repositories.PortfolioRepository;
import br.com.murilocb123.portflow.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Override
    public PortfolioEntity create(PortfolioEntity entity) {
        return portfolioRepository.save(entity);
    }

    @Override
    public PortfolioEntity getById(UUID id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Portfolio not found"));
    }

    @Override
    public PortfolioEntity update(UUID id, PortfolioEntity entity) {
        PortfolioEntity existing = getById(id);
        entity.setId(existing.getId());
        return portfolioRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        portfolioRepository.deleteById(id);
    }

    @Override
    public Page<PortfolioEntity> list(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }
}

