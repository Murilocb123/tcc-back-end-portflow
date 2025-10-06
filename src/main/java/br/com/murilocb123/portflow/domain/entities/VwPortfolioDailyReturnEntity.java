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
@View(query = "SELECT * FROM vw_portfolio_daily_returns")
@Table(name = "vw_portfolio_daily_returns")
public class VwPortfolioDailyReturnEntity {

    @Id
    private Long seqId;
    /**
     * Código do tenant
     */
    private UUID tenant;
    /**
     * Portfolio code
     */
    private UUID portfolio;
    /**
     * Portfolio name
     */
    private String portfolioName;
    /**
     * Price date
     */
    private LocalDate priceDate;
    /**
     * Market value at the end of the day
     */
    private BigDecimal mvEnd;
    /**
     * Market value at the beginning of the day
     */
    private BigDecimal mvPrev;
    /**
     * Retorno diário (fração)
     */
    private BigDecimal dailyReturn;
    /**
     * Valor do fluxo do dia
     */
    private BigDecimal flowValue;
    /**
     * Valor real de retorno do dia
     */
    private BigDecimal realReturnValue;
    /**
     * Valor base acumulado
     */
    private BigDecimal baseValue;
    /**
     * Retorno acumulado em percentual
     */
    private BigDecimal cumulativeReturnPercent;
    /**
     * Ganho acumulado em valor
     */
    private BigDecimal cumulativeValueGain;

}
