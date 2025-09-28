package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import br.com.murilocb123.portflow.dto.BrokerFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BrokerService {
    BrokerEntity create(BrokerEntity entity);
    BrokerEntity getById(UUID id);
    BrokerEntity update(UUID id, BrokerEntity entity);
    void delete(UUID id);
    Page<BrokerEntity> list(Pageable pageable, BrokerFilterDTO filter);
    List<BrokerEntity> listAll();
    List<BrokerEntity> listAllByBrokersAvailable(UUID assetId);
}

