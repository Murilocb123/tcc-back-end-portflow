package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.dto.*;
import br.com.murilocb123.portflow.dto.enums.HistoryForecastType;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import br.com.murilocb123.portflow.mapper.PortfolioAssetDailyReturnsMapper;
import br.com.murilocb123.portflow.mapper.PortfolioDailyReturnMapper;
import br.com.murilocb123.portflow.repositories.*;
import br.com.murilocb123.portflow.service.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardServiceImpl implements DashboardService {

    AssetForecastRepository assetForecastRepository;
    PortfolioAssetRepository portfolioAssetRepository;
    VwPortfolioDailyReturnRepository portfolioDailyReturnRepository;
    VwPortfolioMonthlyIncomeRepository portfolioMonthlyIncomeRepository;
    VwPortfolioAssetDailyReturnsRepository portfolioAssetDailyReturnsRepository;

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

    @Override
    public List<PortfolioAssetDailyReturnsDTO> getDetailedDailyReturnsByAsset(UUID assetID) {
        var portfolioId = AppContextHolder.getCurrentPortfolio();
        return portfolioAssetDailyReturnsRepository.findAllByPortfolioAndAsset(portfolioId, assetID).stream()
                .map(PortfolioAssetDailyReturnsMapper::toDTO)
                .toList();
    }

    @Override
    public ForecastAssetDTO getForecastedDailyReturnsByAsset(UUID assetID) {
        var portfolioId = AppContextHolder.getCurrentPortfolio();
        var historyAndForecast = assetForecastRepository.findHistoryAndForecastUnion(assetID).stream()
                .map(item -> new AssetHistoryForecastDTO(
                        HistoryForecastType.valueOf((String) item[0]),
                        ((Date) item[1]).toLocalDate(),
                        (BigDecimal) item[2],
                        (BigDecimal) item[3],
                        (BigDecimal) item[4]
                ))
                .toList();
        var totalQuantity = portfolioAssetRepository.totalQuantityByPortfolioAndAsset(portfolioId, assetID);
        //pega a ultima data disponivel
        var totalForecastValue = historyAndForecast.stream()
                .reduce((first, second) -> second)
                .map(AssetHistoryForecastDTO::value)
                .orElse(BigDecimal.ZERO);
        totalForecastValue = totalForecastValue.multiply(totalQuantity != null ? totalQuantity : BigDecimal.ZERO);
        return new ForecastAssetDTO(
                totalQuantity != null ? totalQuantity : BigDecimal.ZERO,
                totalForecastValue,
                historyAndForecast
        );
    }
}
