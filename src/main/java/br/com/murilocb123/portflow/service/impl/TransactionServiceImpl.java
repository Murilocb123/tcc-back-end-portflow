package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.repositories.TransactionRepository;
import br.com.murilocb123.portflow.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionEntity create(TransactionEntity entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public TransactionEntity getById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found"));
    }

    @Override
    public TransactionEntity update(UUID id, TransactionEntity entity) {
        TransactionEntity existing = getById(id);
        entity.setId(existing.getId());
        return transactionRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Page<TransactionEntity> list(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }
}
