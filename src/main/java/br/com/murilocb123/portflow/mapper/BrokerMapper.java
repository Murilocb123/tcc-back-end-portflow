package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import br.com.murilocb123.portflow.dto.BrokerDTO;

public class BrokerMapper {
    public static BrokerDTO toDTO(BrokerEntity entity) {
        if (entity == null) return null;
        return new BrokerDTO(
            entity.getId(),
            entity.getName(),
            entity.getCnpj()
        );
    }

    public static BrokerEntity toEntity(BrokerDTO dto) {
        if (dto == null) return null;
        BrokerEntity entity = new BrokerEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setCnpj(dto.cnpj());
        return entity;
    }
}

