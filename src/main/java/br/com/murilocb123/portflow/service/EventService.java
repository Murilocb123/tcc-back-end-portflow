package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    EventEntity create(EventEntity entity);
    EventEntity getById(UUID id);
    EventEntity update(UUID id, EventEntity entity);
    void delete(UUID id);
    Page<EventEntity> list(Pageable pageable);
}

