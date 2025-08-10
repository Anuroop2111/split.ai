package com.split.ai.split.service.repository;

import com.split.ai.split.service.repository.entity.LocalCredentialsEntity;
import com.split.ai.split.service.repository.jpa.LocalCredentialsJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocalCredentialsDao {

    private final LocalCredentialsJpaRepository localCredentialsJpaRepository;

    public LocalCredentialsEntity save(LocalCredentialsEntity entity) {
        try {
            return localCredentialsJpaRepository.save(entity);
        } catch (Exception e) {
            log.error("[LocalCredentialsDao : save] : error saving credentials for identity {}", entity.getIdentity().getId(), e);
            throw e;
        }
    }

    public Optional<LocalCredentialsEntity> findByIdentityId(UUID identityId) {
        try {
            return localCredentialsJpaRepository.findByIdentityId(identityId);
        } catch (Exception e) {
            log.error("[LocalCredentialsDao : findByIdentityId] : error finding credentials for identity {}", identityId, e);
            throw e;
        }
    }
}
