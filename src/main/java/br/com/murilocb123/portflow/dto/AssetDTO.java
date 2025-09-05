package br.com.murilocb123.portflow.dto;

import br.com.murilocb123.portflow.domain.enums.AssetType;

import java.util.UUID;

public record AssetDTO(
        UUID id,
        String ticker,
        String name,
        AssetType type,
        String currency,
        String exchange
) {
}
