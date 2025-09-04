package br.com.murilocb123.portflow.dto;

import java.util.UUID;

public record PortfolioDTO(
    UUID id,
    String name,
    Boolean defaultPortfolio,
    UUID tenantId
) {}

