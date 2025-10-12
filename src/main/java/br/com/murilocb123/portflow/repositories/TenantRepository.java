package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {
}

