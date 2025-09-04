package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "asset_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetHistoryEntity {

    @EmbeddedId
    private AssetPriceId id;

    @MapsId("assetId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;

    @Column(name = "close_price", nullable = false, precision = 18, scale = 6)
    private BigDecimal closePrice;

    @Column(name = "open_price", precision = 18, scale = 6)
    private BigDecimal openPrice;

    @Column(name = "high_price", precision = 18, scale = 6)
    private BigDecimal highPrice;

    @Column(name = "low_price", precision = 18, scale = 6)
    private BigDecimal lowPrice;

    @Column(name = "volume")
    private Long volume;

}