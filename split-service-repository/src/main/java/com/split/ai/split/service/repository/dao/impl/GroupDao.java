package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;
import com.split.ai.split.service.repository.dao.IGroupDao;
import com.split.ai.split.service.repository.entity.GroupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Data access object for {@link GroupEntity}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GroupDao implements IGroupDao {

    private final PostgresClient postgresClient;

    @Override
    public void save(GroupEntity entity) {
        log.debug("[GroupDao : save] : {}", entity);
        try {
            entity.beforeInsertOrUpdate();
            postgresClient.insert(entity);
        } catch (Exception e) {
            log.error("[GroupDao : save] : error saving group {}", entity.getGroupId(), e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void update(GroupEntity entity) {
        log.debug("[GroupDao : update] : {}", entity);
        try {
            entity.beforeUpdate();
            postgresClient.partialUpdate(entity);
        } catch (Exception e) {
            log.error("[GroupDao : update] : error updating group {}", entity.getGroupId(), e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public GroupEntity findById(UUID groupId) {
        log.debug("[GroupDao : findById] : {}", groupId);
        try {
            return postgresClient.findById(GroupEntity.class, groupId);
        } catch (Exception e) {
            log.error("[GroupDao : findById] : error fetching group {}", groupId, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<GroupEntity> findByUserIdPaginated(UUID userId, Integer page, Integer size) {
        log.debug("[GroupDao : findByUserIdPaginated] : user {} page {} size {}", userId, page, size);
        try {
            String sql = "SELECT g.* FROM groups g " +
                    "JOIN user_group ug ON g.groupId = ug.groupId " +
                    "WHERE ug.userId = :userId " +
                    "ORDER BY g.createdAt DESC " +
                    "LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("limit", size);
            params.put("offset", (page - 1L) * size);
            return postgresClient.queryNative(sql, params, GroupEntity.class);
        } catch (Exception e) {
            log.error("[GroupDao : findByUserIdPaginated] : error fetching groups for user {} page {} size {}", userId, page, size, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }
}

