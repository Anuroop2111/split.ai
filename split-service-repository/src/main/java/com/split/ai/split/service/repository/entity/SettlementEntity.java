package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "settlement")
public class SettlementEntity {

    @Id
    private UUID settlementId;

    @Column(name = "groupId", nullable = true)
    private UUID groupId;

    @Column(name = "from_user", nullable = false)
    private UUID fromUserId;

    @Column(name = "to_user", nullable = false)
    private UUID toUserId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "text")
    private String note;

    @Column(nullable = false)
    private Long createdAt;
}
