package br.com.murilocb123.portflow.dto;

import br.com.murilocb123.portflow.domain.enums.EventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        UUID id,
        BrokerDTO broker,
        AssetDTO asset,
        EventType type,
        LocalDate eventDate,
        BigDecimal totalValue,
        String notes
) {
}
