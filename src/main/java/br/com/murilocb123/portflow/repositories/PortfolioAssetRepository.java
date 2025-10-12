package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortfolioAssetRepository extends JpaRepository<PortfolioAssetEntity, UUID> {

    Optional<PortfolioAssetEntity> findByPortfolioIdAndAssetIdAndBrokerId(UUID portfolioId, UUID assetId, UUID brokerId);

    Page<PortfolioAssetEntity> findAllByPortfolioId(UUID portfolioId, Pageable pageable);

    @Query(
            value = """
                     SELECT SUM(pa.quantity *
                               (SELECT ah.close_price FROM asset_history ah WHERE ah.asset = pa.asset ORDER BY ah.price_date DESC LIMIT 1))
                    FROM portfolio_asset pa
                    WHERE pa.portfolio = :portfolioId
                    """,
            nativeQuery = true)
    BigDecimal totalPortfolioValue(UUID portfolioId);

    @Query(
            value = """
                    SELECT SUM(pa.quantity)
                    FROM portfolio_asset pa
                    WHERE pa.portfolio = :portfolioId AND pa.asset = :assetId
                    """,
            nativeQuery = true)
    BigDecimal totalQuantityByPortfolioAndAsset(UUID portfolioId, UUID assetId);

    void deleteByPortfolioId(UUID portfolioId);

}

