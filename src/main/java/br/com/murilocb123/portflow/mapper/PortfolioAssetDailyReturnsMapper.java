package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.dto.PortfolioAssetDailyReturnsDTO;
import br.com.murilocb123.portflow.domain.entities.VwPortfolioAssetDailyReturnsEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PortfolioAssetDailyReturnsMapper {
    public static PortfolioAssetDailyReturnsDTO toDTO(VwPortfolioAssetDailyReturnsEntity entity) {
        if (entity == null) return null;
        return new PortfolioAssetDailyReturnsDTO(
            entity.getSeqId(),
            entity.getTenant(),
            entity.getPortfolio(),
            entity.getPortfolioName(),
            entity.getAsset(),
            entity.getPriceDate(),
            entity.getMvEndAsset(),
            entity.getMvPrevAsset(),
            entity.getFlowValueAsset(),
            entity.getRealReturnValueAsset(),
            entity.getDailyReturnAsset(),
            entity.getBaseValueAsset(),
            entity.getCumulativeReturnPercentAsset(),
            entity.getCumulativeValueGainAsset()
        );
    }
}

