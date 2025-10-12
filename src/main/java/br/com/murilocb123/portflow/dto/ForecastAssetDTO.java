package br.com.murilocb123.portflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ForecastAssetDTO(
        BigDecimal totalQuantity,
        BigDecimal totalForecastValue,
        BigDecimal totalNowValue,
        LocalDate lastPriceDate,
        List<AssetHistoryForecastDTO> historyForecast
) {
}
