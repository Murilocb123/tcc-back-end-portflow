package br.com.murilocb123.portflow.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Immutable
@View(query = "SELECT * FROM vw_portfolio_asset_daily_returns")
@Table(name = "vw_portfolio_asset_daily_returns")
public class VwPortfolioAssetDailyReturnsEntity {
    @Id
    private Long seqId;
    private UUID tenant;
    private UUID portfolio;
    private String portfolioName;
    private UUID asset;
    private LocalDate priceDate;
    private BigDecimal mvEndAsset;
    private BigDecimal mvPrevAsset;
    private BigDecimal flowValueAsset;
    private BigDecimal realReturnValueAsset;
    private BigDecimal dailyReturnAsset;
    private BigDecimal baseValueAsset;
    private BigDecimal cumulativeReturnPercentAsset;
    private BigDecimal cumulativeValueGainAsset;
}

