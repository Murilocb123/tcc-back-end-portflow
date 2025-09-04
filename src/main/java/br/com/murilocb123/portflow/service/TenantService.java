package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {
    TenantEntity create(TenantEntity entity);
    TenantEntity getById(String id);
    TenantEntity update(String id, TenantEntity entity);
    void delete(String id);
    Page<TenantEntity> list(Pageable pageable);
}

