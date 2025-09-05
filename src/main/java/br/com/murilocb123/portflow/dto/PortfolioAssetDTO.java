package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record PortfolioAssetDTO(
        UUID id,
        Double quantity,
        Double averagePrice,
        Double totalValue,
        BrokerDTO broker,
        AssetDTO asset
) {
}
