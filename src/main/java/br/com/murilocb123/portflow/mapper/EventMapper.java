package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import br.com.murilocb123.portflow.domain.enums.EventType;
import br.com.murilocb123.portflow.dto.EventDTO;

public class EventMapper {
    public static EventDTO toDTO(EventEntity entity) {
        if (entity == null) return null;
        return new EventDTO(
            entity.getId(),
            entity.getBroker() != null ? entity.getBroker().getId() : null,
            entity.getAsset() != null ? entity.getAsset().getId() : null,
            entity.getEventType() != null ? entity.getEventType().name() : null,
            entity.getValue(),
            entity.getEventDate(),
            entity.getDescription()
        );
    }

    public static EventEntity toEntity(EventDTO dto) {
        if (dto == null) return null;
        EventEntity entity = new EventEntity();
        entity.setId(dto.id());
        entity.setValue(dto.value());
        entity.setEventDate(dto.eventDate());
        entity.setDescription(dto.description());
        entity.setEventType(dto.eventType() != null ? EventType.valueOf(dto.eventType()) : null);
        // Broker e Asset devem ser setados via service/repository se necessário
        return entity;
    }
}

