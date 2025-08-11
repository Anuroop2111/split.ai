package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;
import com.split.ai.split.service.repository.dao.ISettlementDao;
import com.split.ai.split.service.repository.entity.SettlementEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Data access object for {@link SettlementEntity}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class SettlementDao implements ISettlementDao {

    private final PostgresClient postgresClient;

    @Override
    public void save(SettlementEntity entity) {
        log.debug("[SettlementDao : save] : {}", entity);
        try {
            entity.beforeInsertOrUpdate();
            postgresClient.insert(entity);
        } catch (Exception e) {
            log.error("[SettlementDao : save] : error saving settlement {}", entity.getSettlementId(), e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void update(SettlementEntity entity) {
        log.debug("[SettlementDao : update] : {}", entity);
        try {
            entity.beforeInsertOrUpdate();
            postgresClient.partialUpdate(entity);
        } catch (Exception e) {
            log.error("[SettlementDao : update] : error updating settlement {}", entity.getSettlementId(), e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public SettlementEntity findById(UUID settlementId) {
        log.debug("[SettlementDao : findById] : {}", settlementId);
        try {
            return postgresClient.findById(SettlementEntity.class, settlementId);
        } catch (Exception e) {
            log.error("[SettlementDao : findById] : error fetching settlement {}", settlementId, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }
}
