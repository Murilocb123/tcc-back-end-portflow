package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.AssetForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssetForecastRepository extends JpaRepository<AssetForecastEntity, UUID> {


    @Query(value = """
            SELECT 'HISTORY' AS tipo, ah.price_date AS data, ah.close_price AS valor, NULL AS yhat_lower, NULL AS yhat_upper
            FROM asset_history ah
            WHERE ah.asset = :assetId
              AND ah.price_date >= (SELECT MAX(price_date) - INTERVAL '1 year' FROM asset_history WHERE asset = :assetId)
              AND ah.price_date <= (SELECT MAX(price_date) FROM asset_history WHERE asset = :assetId)
            UNION ALL
            SELECT 'FORECAST' AS tipo, af.forecast_date AS data, af.yhat AS valor, af.yhat_lower, af.yhat_upper
            FROM asset_forecast af
            WHERE af.asset = :assetId
              AND af.forecast_date > (SELECT MAX(price_date) FROM asset_history WHERE asset = :assetId)
            ORDER BY data""",
            nativeQuery = true)
    List<Object[]> findHistoryAndForecastUnion(@Param("assetId") UUID assetId);
}
