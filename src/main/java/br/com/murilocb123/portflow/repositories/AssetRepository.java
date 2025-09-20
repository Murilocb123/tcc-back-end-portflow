package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<AssetEntity, UUID> {

    Page<AssetEntity> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}

