package br.com.murilocb123.portflow.utils;

import br.com.murilocb123.portflow.domain.AbstractPortfolioEntity;
import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.infra.security.AppContextHolder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PortfolioUtils {
    public static void addPortfolioToEntity(AbstractPortfolioEntity entity) {
        if (entity.getPortfolio() == null) {
            var portfolio = new PortfolioEntity();
            portfolio.setId(AppContextHolder.getCurrentPortfolio());
            entity.setPortfolio(portfolio);
        }
    }
}
