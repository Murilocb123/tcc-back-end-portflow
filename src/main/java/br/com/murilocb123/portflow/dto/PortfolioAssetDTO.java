package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PortfolioAssetDTO(
        UUID id,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal totalFee,
        BigDecimal totalTax,
        BrokerDTO broker,
        AssetDTO asset
) {
}
