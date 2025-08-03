package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
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
        entity.beforeInsertOrUpdate();
        postgresClient.insert(entity);
    }

    @Override
    public void update(GroupEntity entity) {
        log.debug("[GroupDao : update] : {}", entity);
        entity.beforeInsertOrUpdate();
        postgresClient.partialUpdate(entity);
    }

    @Override
    public GroupEntity findById(UUID groupId) {
        log.debug("[GroupDao : findById] : {}", groupId);
        return postgresClient.findById(GroupEntity.class, groupId);
    }

    @Override
    public List<GroupEntity> findByUserIdPaginated(UUID userId, Integer page, Integer size) {
        log.debug("[GroupDao : findByUserIdPaginated] : user {} page {} size {}", userId, page, size);
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
    }
}

