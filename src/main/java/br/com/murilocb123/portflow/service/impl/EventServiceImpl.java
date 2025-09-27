package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.repositories.EventRepository;
import br.com.murilocb123.portflow.service.EventService;
import br.com.murilocb123.portflow.utils.PortfolioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public EventEntity create(EventEntity entity) {
        PortfolioUtils.addPortfolioToEntity(entity);
        return eventRepository.save(entity);
    }

    @Override
    public EventEntity getById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Event not found"));
    }

    @Override
    public EventEntity update(UUID id, EventEntity entity) {
        EventEntity existing = getById(id);
        entity.setId(existing.getId());
        PortfolioUtils.addPortfolioToEntity(entity);
        return eventRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Page<EventEntity> list(Pageable pageable) {
        return eventRepository.findAllByPortfolioId(AppContextHolder.getCurrentPortfolio(), pageable);
    }
}

