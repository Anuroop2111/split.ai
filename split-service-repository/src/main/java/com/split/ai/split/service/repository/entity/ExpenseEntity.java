package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.CurrencyType;
import com.split.ai.split.service.model.enums.ExpenseStatus;
import com.split.ai.split.service.model.enums.SplitMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    private UUID expenseId;

    @Column(name = "groupId", nullable = true)
    private UUID groupId;

    private String description;

    @Column(name = "payerId", nullable = false)
    private UUID payerId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SplitMode splitMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Column(name = "subCategory", nullable = false)
    private String subCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus expenseStatus;

    @Column(nullable = false)
    private Long expenseDate;

    @Column(columnDefinition = "text")
    private String metaData;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;
}
