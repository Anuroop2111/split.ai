package com.split.ai.split.service.repository.jpa;

import com.split.ai.split.service.repository.entity.IdentityEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityJpaRepository extends JpaRepository<IdentityEntity, UUID> {
    Optional<IdentityEntity> findByUserNameOrEmailId(String userName, String emailId);
}
