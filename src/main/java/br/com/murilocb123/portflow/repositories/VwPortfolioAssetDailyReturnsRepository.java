package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.VwPortfolioAssetDailyReturnsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VwPortfolioAssetDailyReturnsRepository extends JpaRepository<VwPortfolioAssetDailyReturnsEntity, Long> {
    List<VwPortfolioAssetDailyReturnsEntity> findAllByPortfolioAndAsset(UUID portfolio, UUID asset);
}

