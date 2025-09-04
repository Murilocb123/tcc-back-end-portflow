package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "portfolio",
        uniqueConstraints =
        @UniqueConstraint(name = "uk_portfolio_name_tenant", columnNames = {"name", "tenant_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "default_porfolio", nullable = false)
    private Boolean defaultPortfolio = false;

}
