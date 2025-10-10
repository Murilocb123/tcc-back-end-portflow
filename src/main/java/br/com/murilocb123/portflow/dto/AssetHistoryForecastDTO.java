package br.com.murilocb123.portflow.dto;

import br.com.murilocb123.portflow.dto.enums.HistoryForecastType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssetHistoryForecastDTO(
        HistoryForecastType type,
        LocalDate date,
        BigDecimal value,
        BigDecimal yhatLower,
        BigDecimal yhatUpper
) {}
