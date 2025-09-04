package br.com.murilocb123.portflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TenantDTO(
    UUID id,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

