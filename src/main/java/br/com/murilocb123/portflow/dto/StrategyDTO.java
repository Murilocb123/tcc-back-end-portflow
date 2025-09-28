package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record StrategyDTO(
        UUID id,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal totalInvested,
        BigDecimal totalFee,
        BigDecimal totalTax,
        BigDecimal totalReceivable,
        BrokerDTO broker,
        AssetDTO asset,
        LocalDate startDate,
        BigDecimal targetPrice
) {
}
