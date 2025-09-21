package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractPortfolioEntity;
import br.com.murilocb123.portflow.domain.enums.TxnType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends AbstractPortfolioEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker")
    private BrokerEntity broker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TxnType type;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "quantity", precision = 28, scale = 10)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "price", precision = 18, scale = 6)
    private BigDecimal price;

    @Column(name = "gross_value", precision = 18, scale = 2)
    private BigDecimal grossValue = BigDecimal.ZERO;

    @Column(name = "net_value", precision = 18, scale = 2)
    private BigDecimal netValue = BigDecimal.ZERO;

    @Column(name = "fee_value", precision = 18, scale = 2)
    private BigDecimal feeValue = BigDecimal.ZERO;

    @Column(name = "tax_value", precision = 18, scale = 2)
    private BigDecimal taxValue = BigDecimal.ZERO;

    @Column(name = "description")
    private String description;
}