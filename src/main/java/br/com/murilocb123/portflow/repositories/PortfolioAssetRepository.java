package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAssetEntity, UUID> {
}

