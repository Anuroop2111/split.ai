package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.IDENTITY_PROVIDER;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "identity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "identifier"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityEntity {

    @Id
    private UUID identityId;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IDENTITY_PROVIDER provider;

    @Column(nullable = false, columnDefinition = "CITEXT")
    private String identifier;

    @Column(nullable = false)
    @Builder.Default
    private Boolean verified = Boolean.FALSE;

    @Column(nullable = false, updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate() {
        long now = System.currentTimeMillis();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
        if (verified == null) {
            verified = Boolean.FALSE;
        }
    }

    public void beforeUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}
