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
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "identity_id", nullable = false)
    private UUID identityId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private IDENTITY_PROVIDER provider;

    @Column(name = "identifier", nullable = false, columnDefinition = "CITEXT")
    private String identifier;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
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
