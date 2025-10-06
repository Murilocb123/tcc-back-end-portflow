package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PortfolioDailyReturnDTO(
    Long seqId,
    UUID tenant,
    UUID portfolio,
    String portfolioName,
    LocalDate priceDate,
    BigDecimal mvEnd,
    BigDecimal mvPrev,
    BigDecimal flowValue,
    BigDecimal realReturnValue,
    BigDecimal dailyReturn,
    BigDecimal baseValue,
    BigDecimal cumulativeReturnPercent,
    BigDecimal cumulativeValueGain
) {}
