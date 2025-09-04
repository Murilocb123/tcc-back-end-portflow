package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.UserEntity;
import br.com.murilocb123.portflow.dto.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(UserEntity entity) {
        if (entity == null) return null;
        return new UserDTO(
            entity.getId(),
            entity.getEmail(),
            entity.getName(),
            entity.isActive(),
            entity.getTenantId()
        );
    }

    public static UserEntity toEntity(UserDTO dto) {
        if (dto == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(dto.id());
        entity.setEmail(dto.email());
        entity.setName(dto.name());
        entity.setActive(dto.active());
        entity.setTenantId(dto.tenantId());
        return entity;
    }
}

