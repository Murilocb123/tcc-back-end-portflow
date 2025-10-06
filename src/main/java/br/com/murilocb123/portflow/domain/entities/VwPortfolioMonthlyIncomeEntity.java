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
@View(query = "SELECT * FROM vw_portfolio_monthly_income")
@Table(name = "vw_portfolio_monthly_income")
public class VwPortfolioMonthlyIncomeEntity {
    @Id
    private Long seqId;
    private UUID portfolio;
    private String portfolioName;
    private LocalDate monthStart;
    private BigDecimal income;
}

