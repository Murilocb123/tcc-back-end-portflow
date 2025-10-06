package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.VwPortfolioDailyReturnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VwPortfolioDailyReturnRepository extends JpaRepository<VwPortfolioDailyReturnEntity, Long> {
    // Métodos customizados, se necessário
    List<VwPortfolioDailyReturnEntity> findAllByPortfolio(UUID portfolio);
}
