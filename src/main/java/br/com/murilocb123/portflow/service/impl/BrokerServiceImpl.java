package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import br.com.murilocb123.portflow.dto.BrokerFilterDTO;
import br.com.murilocb123.portflow.repositories.BrokerRepository;
import br.com.murilocb123.portflow.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrokerServiceImpl implements BrokerService {
    private final BrokerRepository brokerRepository;

    @Override
    public BrokerEntity create(BrokerEntity entity) {
        return brokerRepository.save(entity);
    }

    @Override
    public BrokerEntity getById(UUID id) {
        return brokerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Broker not found"));
    }

    @Override
    public BrokerEntity update(UUID id, BrokerEntity entity) {
        BrokerEntity existing = getById(id);
        entity.setId(existing.getId());
        return brokerRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        brokerRepository.deleteById(id);
    }

    @Override
    public Page<BrokerEntity> list(Pageable pageable, BrokerFilterDTO filter) {
        if (filter != null && filter.name() != null) {
            return brokerRepository.findAllByNameContainsIgnoreCase(filter.name(), pageable);
        }
        return brokerRepository.findAll(pageable);
    }

    @Override
    public List<BrokerEntity> listAll() {
        return brokerRepository.findAll();
    }
}

