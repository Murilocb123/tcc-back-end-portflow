package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "portfolio")  
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioEntity extends AbstractTenantEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "default_portfolio", nullable = false)
    private Boolean defaultPortfolio = false;

}
