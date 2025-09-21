package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractPortfolioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "portfolio_asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAssetEntity extends AbstractPortfolioEntity {

    // colunas de preço medio, quantidade, broker, etc

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "average_price", nullable = false)
    private BigDecimal averagePrice = BigDecimal.ZERO;

    @Column(name = "total_fee", nullable = false)
    private BigDecimal totalFee = BigDecimal.ZERO;

    @Column(name = "total_tax", nullable = false)
    private BigDecimal totalTax = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker")
    private BrokerEntity broker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;


}
