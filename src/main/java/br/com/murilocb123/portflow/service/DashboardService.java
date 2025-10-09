package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.dto.OverviewDashboardDTO;
import br.com.murilocb123.portflow.dto.PortfolioAssetDailyReturnsDTO;

import java.util.List;
import java.util.UUID;

public interface DashboardService {
    OverviewDashboardDTO getPortfolioOverview();

    List<PortfolioAssetDailyReturnsDTO> getDetailedDailyReturnsByAsset(UUID assetID);
}