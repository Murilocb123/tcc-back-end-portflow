package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record AssetDTO(
    UUID id,
    String ticker,
    String name,
    String assetClass,
    String currency
) {}

