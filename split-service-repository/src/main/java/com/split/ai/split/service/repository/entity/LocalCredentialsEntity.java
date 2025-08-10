package com.split.ai.split.service.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local_credentials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalCredentialsEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "identity_id", nullable = false, unique = true)
    private IdentityEntity identity;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
}
