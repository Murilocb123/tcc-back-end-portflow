package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record PortfolioAssetDTO(
    UUID id,
    UUID assetId,
    UUID portfolioId,
    UUID brokerId,
    Double quantity,
    Double averagePrice,
    Double totalValue
) {}

