package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractPortfolioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "portfolio_asset",
        uniqueConstraints =
        @UniqueConstraint(name = "uk_portfolio_asset_asset_portfolio_broker", columnNames = {"asset", "portfolio_id", "broker"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAssetEntity extends AbstractPortfolioEntity {

    // colunas de preço medio, quantidade, broker, etc

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "average_price", nullable = false)
    private Double averagePrice;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker")
    private BrokerEntity broker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;


}
