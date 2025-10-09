package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PortfolioAssetDailyReturnsDTO(
    Long seqId,
    UUID tenant,
    UUID portfolio,
    String portfolioName,
    UUID asset,
    LocalDate priceDate,
    BigDecimal mvEndAsset,
    BigDecimal mvPrevAsset,
    BigDecimal flowValueAsset,
    BigDecimal realReturnValueAsset,
    BigDecimal dailyReturnAsset,
    BigDecimal baseValueAsset,
    BigDecimal cumulativeReturnPercentAsset,
    BigDecimal cumulativeValueGainAsset
) {}
