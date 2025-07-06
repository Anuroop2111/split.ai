package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.repository.entity.enums.CurrencyType;
import com.split.ai.split.service.repository.entity.enums.ExpenseStatus;
import com.split.ai.split.service.repository.entity.enums.SplitMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private GroupEntity group;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payerId")
    private UserEntity payer;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private SplitMode splitMode;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus expenseStatus;

    private Long expenseDate;

    @Column(columnDefinition = "text")
    private String metaData;

    private Long createdAt;

    private Long updatedAt;
}
