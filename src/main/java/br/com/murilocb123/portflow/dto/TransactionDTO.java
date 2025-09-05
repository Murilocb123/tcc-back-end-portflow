package br.com.murilocb123.portflow.dto;

import br.com.murilocb123.portflow.domain.enums.TxnType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDTO(
        UUID id,
        BrokerDTO broker,
        AssetDTO asset,
        PortfolioDTO portfolio,
        TxnType type,
        LocalDate tradeDate,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal grossValue,
        BigDecimal netValue,
        BigDecimal feeValue,
        BigDecimal taxValue,
        String description
) {
}
