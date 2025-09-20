package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}

