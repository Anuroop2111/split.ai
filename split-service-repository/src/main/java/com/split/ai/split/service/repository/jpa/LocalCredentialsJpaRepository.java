package com.split.ai.split.service.repository.jpa;

import com.split.ai.split.service.repository.entity.LocalCredentialsEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalCredentialsJpaRepository extends JpaRepository<LocalCredentialsEntity, UUID> {
    Optional<LocalCredentialsEntity> findByIdentityId(UUID identityId);
}
