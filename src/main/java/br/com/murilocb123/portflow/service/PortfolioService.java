package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PortfolioService {
    PortfolioEntity create(PortfolioEntity entity);
    void createDefaultPortfolioIfNotExists();
    PortfolioEntity getById(UUID id);
    PortfolioEntity update(UUID id, PortfolioEntity entity);
    void delete(UUID id);
    Page<PortfolioEntity> list(Pageable pageable);
    List<PortfolioEntity> listAll();


}

