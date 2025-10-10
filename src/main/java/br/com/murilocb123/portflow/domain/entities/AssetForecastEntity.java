package br.com.murilocb123.portflow.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset_forecast")
public class AssetForecastEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;

    @Column(name = "forecast_date", nullable = false)
    private LocalDate forecastDate;

    @Column(nullable = false)
    private BigDecimal yhat;

    @Column(name = "yhat_lower")
    private BigDecimal yhatLower;

    @Column(name = "yhat_upper")
    private BigDecimal yhatUpper;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private UUID createdBy;
}

