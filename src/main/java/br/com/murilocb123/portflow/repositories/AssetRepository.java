package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<AssetEntity, UUID> {

    Page<AssetEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

    @Query("""
                SELECT DISTINCT a
                  FROM AssetEntity a
            INNER JOIN PortfolioAssetEntity pa
                    ON pa.asset.id = a.id
                 WHERE pa.asset.id = :assetId
                   AND pa.quantity > 0
                   AND pa.portfolio.id = :portfolioId
            """)
    List<AssetEntity> findAllByAssetsIdAvailable(UUID assetId, UUID portfolioId);
}

