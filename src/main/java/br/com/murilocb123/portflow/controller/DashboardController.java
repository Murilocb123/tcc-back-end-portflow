package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.dto.OverviewDashboardDTO;
import br.com.murilocb123.portflow.dto.PortfolioDailyReturnDTO;
import br.com.murilocb123.portflow.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {
    DashboardService dashboardService;

    @GetMapping("/portfolio-daily-returns")
    public List<PortfolioDailyReturnDTO> getPortfolioDailyReturns() {
        return dashboardService.getPortfolioDailyReturns();
    }


    @GetMapping("/overview")
    public OverviewDashboardDTO getPortfolioOverview() {
        return dashboardService.getPortfolioOverview();
    }

}
