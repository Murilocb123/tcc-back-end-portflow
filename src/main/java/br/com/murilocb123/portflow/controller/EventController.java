package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import br.com.murilocb123.portflow.dto.EventDTO;
import br.com.murilocb123.portflow.service.EventService;
import br.com.murilocb123.portflow.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public EventDTO create(@RequestBody EventDTO dto) {
        EventEntity entity = EventMapper.toEntity(dto);
        EventEntity saved = eventService.create(entity);
        return EventMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public EventDTO getById(@PathVariable UUID id) {
        return EventMapper.toDTO(eventService.getById(id));
    }

    @PutMapping("/{id}")
    public EventDTO update(@PathVariable UUID id, @RequestBody EventDTO dto) {
        EventEntity entity = EventMapper.toEntity(dto);
        EventEntity updated = eventService.update(id, entity);
        return EventMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        eventService.delete(id);
    }

    @GetMapping
    public Page<EventDTO> list(Pageable pageable) {
        Page<EventEntity> page = eventService.list(pageable);
        List<EventDTO> dtos = page.getContent().stream().map(EventMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }
}
