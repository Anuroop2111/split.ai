package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.CURRENCY;
import com.split.ai.split.service.model.enums.GROUP_TYPE;
import com.split.ai.split.service.model.enums.GROUP_STATUS;
import com.split.ai.split.service.model.enums.SETTLE_MODE;
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
    private GROUP_TYPE groupType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CURRENCY currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SETTLE_MODE settleMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GROUP_STATUS groupStatus;

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
