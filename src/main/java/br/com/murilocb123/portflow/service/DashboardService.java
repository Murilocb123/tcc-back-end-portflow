package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.dto.OverviewDashboardDTO;
import br.com.murilocb123.portflow.dto.PortfolioDailyReturnDTO;

import java.util.List;

public interface DashboardService {
    List<PortfolioDailyReturnDTO> getPortfolioDailyReturns();

    OverviewDashboardDTO getPortfolioOverview();
}
