package com.split.ai.split.service.repository;

import com.split.ai.split.service.repository.entity.IdentityEntity;
import com.split.ai.split.service.repository.jpa.IdentityJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IdentityDao {

    private final IdentityJpaRepository identityJpaRepository;

    public IdentityEntity save(IdentityEntity identityEntity) {
        try {
            return identityJpaRepository.save(identityEntity);
        } catch (Exception e) {
            log.error("[IdentityDao : save] : error saving identity {}", identityEntity, e);
            throw e;
        }
    }

    public Optional<IdentityEntity> findByUserNameOrEmailId(String userName, String emailId) {
        try {
            return identityJpaRepository.findByUserNameOrEmailId(userName, emailId);
        } catch (Exception e) {
            log.error("[IdentityDao : findByUserNameOrEmailId] : error finding identity {}", userName, e);
            throw e;
        }
    }

    public Optional<IdentityEntity> findById(UUID id) {
        try {
            return identityJpaRepository.findById(id);
        } catch (Exception e) {
            log.error("[IdentityDao : findById] : error finding identity {}", id, e);
            throw e;
        }
    }
}
