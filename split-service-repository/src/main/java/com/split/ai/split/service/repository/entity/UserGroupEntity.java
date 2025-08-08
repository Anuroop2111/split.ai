package com.split.ai.split.service.repository.entity;

import com.split.ai.split.service.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_group")
public class UserGroupEntity {

    @EmbeddedId
    private UserGroupKey id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Long createdAt;

    public void beforeInsert() {
        if (createdAt == null) {
            createdAt = System.currentTimeMillis();
        }
    }
}
