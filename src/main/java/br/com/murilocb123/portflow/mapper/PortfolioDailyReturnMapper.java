package br.com.murilocb123.portflow.mapper;

import br.com.murilocb123.portflow.dto.PortfolioDailyReturnDTO;
import br.com.murilocb123.portflow.domain.entities.VwPortfolioDailyReturnEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PortfolioDailyReturnMapper {
    public static PortfolioDailyReturnDTO toDTO(VwPortfolioDailyReturnEntity entity) {
        if (entity == null) return null;
        return new PortfolioDailyReturnDTO(
            entity.getSeqId(),
            entity.getTenant(),
            entity.getPortfolio(),
            entity.getPortfolioName(),
            entity.getPriceDate(),
            entity.getMvEnd(),
            entity.getMvPrev(),
            entity.getFlowValue(),
            entity.getRealReturnValue(),
            entity.getDailyReturn(),
            entity.getBaseValue(),
            entity.getCumulativeReturnPercent(),
            entity.getCumulativeValueGain()
        );
    }
}
