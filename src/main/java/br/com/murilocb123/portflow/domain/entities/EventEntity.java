package br.com.murilocb123.portflow.domain.entities;

import br.com.murilocb123.portflow.domain.AbstractPortfolioEntity;
import br.com.murilocb123.portflow.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends AbstractPortfolioEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker")
    private BrokerEntity broker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", nullable = false)
    private AssetEntity asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EventType type;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "total_value", precision = 18, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "notes")
    private String notes;
}
