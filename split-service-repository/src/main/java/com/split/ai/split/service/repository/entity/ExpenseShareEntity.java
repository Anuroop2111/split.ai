package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

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
@Table(name = "expense_share")
public class ExpenseShareEntity {

    @EmbeddedId
    private ExpenseShareKey id;

    @Column(nullable = false)
    private BigDecimal sharedAmount;
}
