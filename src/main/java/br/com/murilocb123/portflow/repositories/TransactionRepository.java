package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {


    Page<TransactionEntity> findAllByPortfolioId(UUID portfolioId, Pageable pageable);

    // Calcular preço medio de compra de um ativo em um portfólio
    @Query("""
            SELECT
                  SUM(t.netValue) / SUM(t.quantity)
             FROM TransactionEntity t
            WHERE
                  t.portfolio.id = :portfolioId
              AND t.asset.id = :assetId
              AND t.type = 'BUY'
            """)
    BigDecimal calculateAveragePrice(UUID portfolioId, UUID assetId, UUID brokerId);
}

