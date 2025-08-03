package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.Category;
import com.split.ai.split.service.model.enums.CurrencyType;
import com.split.ai.split.service.model.enums.ExpenseStatus;
import com.split.ai.split.service.model.enums.SplitMode;
import com.split.ai.split.service.model.enums.SubCategory;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Immutable Data. If an Expense gets edited, a new ExpenseRevision will get created
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_revision")
public class ExpenseRevisionEntity {

    @Id
    private UUID expenseRevisionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expenseId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rev_expense"))
    private ExpenseEntity expense;

    /* ---------- who edited/Created & when ---------- */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "editedByUserId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rev_user"))
    private UserEntity editedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Long editedAt;

    /* ---------- snapshot of mutable fields ---------- */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payerId", nullable = false, foreignKey =
    @ForeignKey(name = "fk_expense_payer"))
    private UserEntity payer;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    private Long expenseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SplitMode splitMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus expenseStatus;

    private String description;

    @Column(columnDefinition = "text")
    private String metaData;

    /* ---------- snapshot of user-expense-shares (JSONB) ---------- */
    /**
     * JSON structure: {"userId1":250.00,"userId2":250.00,…}
     * Requires Hibernate Types lib (`com.vladmihalcea:hibernate-types-60`).
     */
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<UUID, BigDecimal> userShares;

    public void beforeInsertOrUpdate() {
        if (editedAt == null) {
            editedAt = System.currentTimeMillis();
        }
    }
}
