package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    TransactionEntity create(TransactionEntity entity);
    TransactionEntity getById(UUID id);
    TransactionEntity update(UUID id, TransactionEntity entity);
    void delete(UUID id);
    Page<TransactionEntity> list(Pageable pageable);
}
