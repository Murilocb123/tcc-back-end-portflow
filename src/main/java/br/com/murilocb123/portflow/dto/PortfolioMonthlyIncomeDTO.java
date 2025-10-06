package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;

public record PortfolioMonthlyIncomeDTO(
        Integer month,
        Integer year,
        BigDecimal totalIncome
) {
}
