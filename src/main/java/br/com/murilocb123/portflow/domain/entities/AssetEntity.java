package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractEntity;
import br.com.murilocb123.portflow.domain.enums.AssetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asset",
        uniqueConstraints =
        @UniqueConstraint(name = "uk_asset_ticker_exchange", columnNames = {"ticker","exchange"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity extends AbstractEntity {

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private AssetType assetClass;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "BRL";

    @Column(name = "exchange")
    private String exchange;

}
