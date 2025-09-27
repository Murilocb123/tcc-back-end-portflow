package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAssetEntity, UUID> {

    Optional<PortfolioAssetEntity> findByPortfolioIdAndAssetIdAndBrokerId(UUID portfolioId, UUID assetId, UUID brokerId);
    Page<PortfolioAssetEntity> findAllByPortfolioId(UUID portfolioId, Pageable pageable);
}

