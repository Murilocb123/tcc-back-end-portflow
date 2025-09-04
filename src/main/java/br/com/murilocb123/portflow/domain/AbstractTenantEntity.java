package br.com.murilocb123.portflow.domain;

import br.com.murilocb123.portflow.domain.entities.TenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTenantEntity  extends AbstractEntity {

    @TenantId
    @Column(name = "tenant", nullable = false, updatable = false)
    private UUID tenantId;
}
