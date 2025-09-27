package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Page<EventEntity> findAllByPortfolioId(UUID portfolioId, Pageable pageable);

}

