package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
    UUID id,
    UUID brokerId,
    UUID assetId,
    String eventType,
    BigDecimal value,
    LocalDate eventDate,
    String description
) {}

