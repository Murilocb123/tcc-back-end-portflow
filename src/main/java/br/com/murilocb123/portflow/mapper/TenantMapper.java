package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import br.com.murilocb123.portflow.dto.TenantDTO;

public class TenantMapper {
    public static TenantDTO toDTO(TenantEntity entity) {
        if (entity == null) return null;
        return new TenantDTO(
            entity.getId(),
            entity.getName(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public static TenantEntity toEntity(TenantDTO dto) {
        if (dto == null) return null;
        TenantEntity entity = new TenantEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setCreatedAt(dto.createdAt());
        entity.setUpdatedAt(dto.updatedAt());
        return entity;
    }
}

