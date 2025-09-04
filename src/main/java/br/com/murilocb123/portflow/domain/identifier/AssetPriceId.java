package br.com.murilocb123.portflow.domain.identifier;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssetPriceId {
    @Column(name = "asset_id", nullable = false)
    private java.util.UUID assetId;

    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;
}
