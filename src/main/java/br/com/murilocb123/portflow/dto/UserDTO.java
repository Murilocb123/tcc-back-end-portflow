package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record UserDTO(
    UUID id,
    String email,
    String name,
    Boolean active,
    UUID tenantId
) {}

