package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.util.List;

public record OverviewDashboardDTO(
        BigDecimal totalPortfolioValue,
        BigDecimal totalReturnPercent,
        BigDecimal totalIncomeValue,
        List<PortfolioDailyReturnDTO> dailyReturns,
        List<PortfolioMonthlyIncomeDTO> monthlyIncomes
) {
}
