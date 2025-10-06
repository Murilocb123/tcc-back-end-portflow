package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.VwPortfolioDailyReturnEntity;
import br.com.murilocb123.portflow.dto.OverviewDashboardDTO;
import br.com.murilocb123.portflow.dto.PortfolioDailyReturnDTO;
import br.com.murilocb123.portflow.dto.PortfolioMonthlyIncomeDTO;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.mapper.PortfolioDailyReturnMapper;
import br.com.murilocb123.portflow.repositories.PortfolioAssetRepository;
import br.com.murilocb123.portflow.repositories.VwPortfolioDailyReturnRepository;
import br.com.murilocb123.portflow.repositories.VwPortfolioMonthlyIncomeRepository;
import br.com.murilocb123.portflow.service.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardServiceImpl implements DashboardService {

    VwPortfolioDailyReturnRepository portfolioDailyReturnRepository;
    PortfolioAssetRepository portfolioAssetRepository;
    VwPortfolioMonthlyIncomeRepository portfolioMonthlyIncomeRepository;


    public List<PortfolioDailyReturnDTO> getPortfolioDailyReturns() {
        var portfolioId = AppContextHolder.getCurrentPortfolio();
        List<VwPortfolioDailyReturnEntity> entities = portfolioDailyReturnRepository.findAllByPortfolio(portfolioId);
        return entities.stream()
                .map(PortfolioDailyReturnMapper::toDTO)
                .toList();
    }

    @Override
    public OverviewDashboardDTO getPortfolioOverview() {
        var portfolioId = AppContextHolder.getCurrentPortfolio();

        var dailyReturns = portfolioDailyReturnRepository.findAllByPortfolio(portfolioId).stream()
                .map(PortfolioDailyReturnMapper::toDTO)
                .toList();

        var cumulativeReturnPercent = dailyReturns.isEmpty()
                ? BigDecimal.ZERO
                : dailyReturns.getLast().cumulativeReturnPercent();

        var totalPortfolioValue = portfolioAssetRepository.totalPortfolioValue(portfolioId);
        var monthlyIncome = portfolioMonthlyIncomeRepository.findAllByPortfolio(portfolioId);

        var totalIncome = monthlyIncome.stream()
                .map(item -> item.getIncome() != null ? item.getIncome() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var monthlyIncomeDto = monthlyIncome.stream()
                .map(item -> new PortfolioMonthlyIncomeDTO(
                        item.getMonthStart().getMonthValue(),
                        item.getMonthStart().getYear(),
                        item.getIncome() != null ? item.getIncome() : BigDecimal.ZERO))
                .toList();

        return new OverviewDashboardDTO(
                totalPortfolioValue != null ? totalPortfolioValue : BigDecimal.ZERO,
                cumulativeReturnPercent,
                totalIncome,
                dailyReturns,
                monthlyIncomeDto
        );
    }
}
