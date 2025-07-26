package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.UpdateTimestamp;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "groupId", foreignKey =
    @ForeignKey(name = "fk_expense_group"))
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currentRevisionId", nullable = false, foreignKey =
    @ForeignKey(name = "fk_expense_current_rev"))
    private ExpenseRevisionEntity currentRevision;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Long createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Long updatedAt;
}
