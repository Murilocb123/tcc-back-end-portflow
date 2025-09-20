package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrokerRepository extends JpaRepository<BrokerEntity, UUID> {

    Page<BrokerEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}

