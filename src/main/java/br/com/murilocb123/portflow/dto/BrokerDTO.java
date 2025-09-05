package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record BrokerDTO(
        UUID id,
        String name,
        String cnpj
) {
}

