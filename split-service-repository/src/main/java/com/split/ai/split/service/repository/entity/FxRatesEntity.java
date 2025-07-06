package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fx_rates")
public class FxRatesEntity {

    @EmbeddedId
    private FxRatesKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("baseCurrency")
    @JoinColumn(name = "baseCurrency")
    private CurrencyEntity base;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quoteCurrency")
    @JoinColumn(name = "quoteCurrency")
    private CurrencyEntity quote;

    @Column(nullable = false)
    private BigDecimal rate;
}
