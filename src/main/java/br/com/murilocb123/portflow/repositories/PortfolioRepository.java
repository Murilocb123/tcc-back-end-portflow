package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {

    @Modifying
    @Query("""
        UPDATE PortfolioEntity p
           SET p.defaultPortfolio = false
         WHERE :portfolioId IS NOT NULL OR p.id <> :portfolioId
            """
    )
    void unsetDefaultPortfolio(UUID portfolioId);

}

