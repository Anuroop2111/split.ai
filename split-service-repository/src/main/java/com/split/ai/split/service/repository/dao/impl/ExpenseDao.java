package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Data access object for {@link ExpenseEntity}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ExpenseDao implements IExpenseDao {

    private final PostgresClient postgresClient;

    @Override
    public List<ExpenseEntity> findByGroupId(UUID groupId) {
        log.debug("[ExpenseDao : findByGroupId] : {}", groupId);
        try {
            String sql = "SELECT * FROM expenses WHERE groupId = :groupId";
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            return postgresClient.queryNative(sql, params, ExpenseEntity.class);
        } catch (Exception e) {
            log.error("[ExpenseDao : findByGroupId] : error fetching expenses for group {}", groupId, e);
            throw new RuntimeException(e);
        }
    }
}
