package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.CurrencyType;
import com.split.ai.split.service.model.enums.GroupStatus;
import com.split.ai.split.service.model.enums.GroupType;
import com.split.ai.split.service.model.enums.SettleMode;
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

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    private UUID groupId;

    @Column(nullable = false)
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupType groupType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SettleMode settleMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupStatus groupStatus;

    @Column(nullable = false, updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    public void beforeInsertOrUpdate() {
        long now = System.currentTimeMillis();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    public void beforeUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}
