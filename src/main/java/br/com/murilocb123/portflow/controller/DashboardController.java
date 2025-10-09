package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.dto.OverviewDashboardDTO;
import br.com.murilocb123.portflow.dto.PortfolioAssetDailyReturnsDTO;
import br.com.murilocb123.portflow.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {
    DashboardService dashboardService;

    @GetMapping("/overview")
    public OverviewDashboardDTO getPortfolioOverview() {
        return dashboardService.getPortfolioOverview();
    }

    @GetMapping("/portfolio-asset-daily-returns/{assetId}")
    public List<PortfolioAssetDailyReturnsDTO> getDetailedDailyReturnsByAsset(@PathVariable UUID assetId) {
        return dashboardService.getDetailedDailyReturnsByAsset(assetId);
    }
}
