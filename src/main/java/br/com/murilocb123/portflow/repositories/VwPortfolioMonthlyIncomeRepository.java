package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.VwPortfolioMonthlyIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VwPortfolioMonthlyIncomeRepository extends JpaRepository<VwPortfolioMonthlyIncomeEntity, Long> {
    List<VwPortfolioMonthlyIncomeEntity> findAllByPortfolio(UUID portfolio);
}

