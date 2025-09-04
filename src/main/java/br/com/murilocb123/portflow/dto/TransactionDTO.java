package br.com.murilocb123.portflow.dto;

import br.com.murilocb123.portflow.domain.enums.TxnType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDTO(
    UUID id,
    UUID brokerId,
    UUID assetId,
    UUID portfolioId,
    TxnType txnType,
    BigDecimal value,
    LocalDate txnDate,
    String description
) {}

