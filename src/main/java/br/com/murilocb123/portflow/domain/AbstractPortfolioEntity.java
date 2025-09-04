package br.com.murilocb123.portflow.domain;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractPortfolioEntity extends AbstractTenantEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio", nullable = false)
    private PortfolioEntity portfolio;
}
