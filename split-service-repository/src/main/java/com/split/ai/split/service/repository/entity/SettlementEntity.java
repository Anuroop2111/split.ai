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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "groupId",
            foreignKey = @ForeignKey(name = "fk_settlement_group"))
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_user",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_settlement_from"))
    private UserEntity fromUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_user",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_settlement_to"))
    private UserEntity toUser;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "text")
    private String note;

    @Column(nullable = false, updatable = false)
    private Long createdAt;
}
