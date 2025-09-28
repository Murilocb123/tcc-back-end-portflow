
package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssetHistoryRepository extends JpaRepository<AssetHistoryEntity, AssetPriceId> {

    @Query("""
            SELECT ah
              FROM AssetHistoryEntity ah
             WHERE (ah.asset.id, ah.id.priceDate) IN (
                   SELECT ah2.asset.id, MAX(ah2.id.priceDate)
                     FROM AssetHistoryEntity ah2
                    WHERE ah2.asset.id IN :assetIds
                    GROUP BY ah2.asset.id
                   )
            """)
    List<AssetHistoryEntity> findLastQuoteByAssetIdIn(List<UUID> assetIds);
}