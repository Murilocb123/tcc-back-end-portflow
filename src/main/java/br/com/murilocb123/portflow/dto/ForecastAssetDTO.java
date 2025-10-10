package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.util.List;

public record ForecastAssetDTO(
        BigDecimal totalQuantity,
        BigDecimal totalForecastValue,
        List<AssetHistoryForecastDTO> historyForecast
) {
}
