package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.infra.exceptions.custom.BusinessException;
import br.com.murilocb123.portflow.repositories.EventRepository;
import br.com.murilocb123.portflow.repositories.PortfolioAssetRepository;
import br.com.murilocb123.portflow.repositories.PortfolioRepository;
import br.com.murilocb123.portflow.repositories.TransactionRepository;
import br.com.murilocb123.portflow.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;
    private final EventRepository eventRepository;
    private final PortfolioAssetRepository portfolioAssetRepository;

    @Override
    public PortfolioEntity create(PortfolioEntity entity) {
        var portfolioQuantity = portfolioRepository.count();
        if (portfolioQuantity >= 5) {
            throw new BusinessException("Quantidade maxima de carterias atingida", "Quantidade maxima de carteiras é de 5.");
        }
        if (Boolean.TRUE == entity.getDefaultPortfolio()) {
            portfolioRepository.unsetDefaultPortfolio(null);
        }
        return portfolioRepository.save(entity);
    }

    @Override
    public void createDefaultPortfolioIfNotExists() {
        long portfolioCount = portfolioRepository.count();
        if (portfolioCount == 0) {
            PortfolioEntity defaultPortfolio = new PortfolioEntity();
            defaultPortfolio.setName("Minha carteira");
            defaultPortfolio.setDefaultPortfolio(true);
            portfolioRepository.save(defaultPortfolio);
        }
    }

    @Override
    public PortfolioEntity getById(UUID id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Portfolio not found"));
    }

    @Override
    @Transactional
    public PortfolioEntity update(UUID id, PortfolioEntity entity) {
        PortfolioEntity existing = getById(id);
        entity.setId(existing.getId());
        if (Boolean.TRUE == entity.getDefaultPortfolio()) {
            portfolioRepository.unsetDefaultPortfolio(entity.getId());
        }
        return portfolioRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        transactionRepository.deleteByPortfolioId(id);
        eventRepository.deleteByPortfolioId(id);
        portfolioAssetRepository.deleteByPortfolioId(id);
        portfolioRepository.deleteById(id);
    }

    @Override
    public Page<PortfolioEntity> list(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }

    @Override
    public List<PortfolioEntity> listAll() {
        return portfolioRepository.findAll();
    }


}

