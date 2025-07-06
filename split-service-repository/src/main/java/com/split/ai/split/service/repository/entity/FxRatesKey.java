package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FxRatesKey implements Serializable {
    private UUID baseCurrency;
    private UUID quoteCurrency;
    private Long createdAt;
}
