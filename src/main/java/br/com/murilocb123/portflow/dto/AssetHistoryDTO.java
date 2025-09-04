package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AssetHistoryDTO(
    UUID assetId,
    LocalDate date,
    BigDecimal closePrice
) {}

