package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.domain.entities.EventEntity;
import br.com.murilocb123.portflow.dto.EventDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EventMapper {
    public static EventDTO toDTO(EventEntity entity) {
        if (entity == null) return null;
        return new EventDTO(
                entity.getId(),
                BrokerMapper.toDTO(entity.getBroker()),
                AssetMapper.toDTO(entity.getAsset()),
                entity.getType(),
                entity.getExDate(),
                entity.getPayDate(),
                entity.getValuePerShare(),
                entity.getTotalValue(),
                entity.getCurrency(),
                entity.getNotes()
        );
    }

    public static EventEntity toEntity(EventDTO dto) {
        if (dto == null) return null;
        EventEntity entity = new EventEntity();
        entity.setId(dto.id());
        entity.setBroker(BrokerMapper.toEntity(dto.broker()));
        entity.setAsset(AssetMapper.toEntity(dto.asset()));
        entity.setType(dto.type());
        entity.setExDate(dto.exDate());
        entity.setPayDate(dto.payDate());
        entity.setValuePerShare(dto.valuePerShare());
        entity.setTotalValue(dto.totalValue());
        entity.setCurrency(dto.currency());
        entity.setNotes(dto.notes());
        return entity;
    }
}
