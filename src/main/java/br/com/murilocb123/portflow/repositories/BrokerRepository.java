package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BrokerRepository extends JpaRepository<BrokerEntity, UUID> {

    Page<BrokerEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

    @Query("""
            SELECT DISTINCT b
              FROM BrokerEntity b
        INNER JOIN PortfolioAssetEntity pa
                ON pa.broker.id = b.id
             WHERE pa.asset.id = :assetId
              AND pa.portfolio.id = :portfolioId
        """)
    List<BrokerEntity> findAllByAssetsIdAvailable(UUID assetId, UUID portfolioId);
}

