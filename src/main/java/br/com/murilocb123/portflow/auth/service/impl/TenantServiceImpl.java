package br.com.murilocb123.portflow.auth.service.impl;


import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import br.com.murilocb123.portflow.repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class TenantServiceImpl {

    TenantRepository tenantRepository;


    @Transactional
    public UUID createTenant(String email) {
        var tenant = new TenantEntity();
        tenant.setName(email);
        return tenantRepository.save(tenant).getId();
    }
}
