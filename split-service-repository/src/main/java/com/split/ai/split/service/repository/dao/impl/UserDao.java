package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.repository.dao.IUserDao;
import com.split.ai.split.service.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Data access object for {@link UserEntity}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDao implements IUserDao {

    private final PostgresClient postgresClient;

    @Override
    public void save(UserEntity entity) {
        log.debug("[UserDao : save] : {}", entity);
        postgresClient.insert(entity);
    }

    @Override
    public void update(UserEntity entity) {
        log.debug("[UserDao : update] : {}", entity);
        postgresClient.partialUpdate(entity);
    }

    @Override
    public UserEntity findById(UUID userId) {
        log.debug("[UserDao : findById] : {}", userId);
        return postgresClient.findById(UserEntity.class, userId);
    }

    @Override
    public List<UserEntity> suggestUsers(String query, Integer limit) {
        log.debug("[UserDao : suggestUsers] : query {} limit {}", query, limit);
        String sql = "SELECT * FROM users " +
                "WHERE user_name % :query " +
                "   OR full_name % :query " +
                "   OR phone % :query " +
                "   OR email_id % :query " +
                "ORDER BY GREATEST(" +
                "   SIMILARITY(user_name, :query)," +
                "   SIMILARITY(full_name, :query)," +
                "   SIMILARITY(phone, :query)," +
                "   SIMILARITY(email_id, :query)" +
                ") DESC LIMIT :limit";
        Map<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("limit", limit);
        return postgresClient.queryNative(sql, params, UserEntity.class);
    }
}

