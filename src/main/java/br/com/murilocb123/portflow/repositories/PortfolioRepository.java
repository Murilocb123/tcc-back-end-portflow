package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {
}

