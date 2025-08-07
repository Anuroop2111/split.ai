package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
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
    public void save(ExpenseEntity entity) {
        log.debug("[ExpenseDao : save] : {}", entity);
        try {
            entity.beforeInsertOrUpdate();
            postgresClient.insert(entity);
        } catch (Exception e) {
            log.error("[ExpenseDao : save] : error saving expense {}", entity.getExpenseId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ExpenseEntity entity) {
        log.debug("[ExpenseDao : update] : {}", entity);
        try {
            entity.beforeUpdate();
            postgresClient.partialUpdate(entity);
        } catch (Exception e) {
            log.error("[ExpenseDao : update] : error updating expense {}", entity.getExpenseId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExpenseEntity findById(UUID expenseId) {
        log.debug("[ExpenseDao : findById] : {}", expenseId);
        try {
            return postgresClient.findById(ExpenseEntity.class, expenseId);
        } catch (Exception e) {
            log.error("[ExpenseDao : findById] : error fetching expense {}", expenseId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveRevision(ExpenseRevisionEntity entity) {
        log.debug("[ExpenseDao : saveRevision] : {}", entity);
        try {
            entity.beforeInsertOrUpdate();
            postgresClient.insert(entity);
        } catch (Exception e) {
            log.error("[ExpenseDao : saveRevision] : error saving revision for expense {}", entity.getExpenseId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExpenseRevisionEntity> findRevisions(UUID expenseId) {
        log.debug("[ExpenseDao : findRevisions] : {}", expenseId);
        try {
            String jpql = "SELECT er FROM ExpenseRevisionEntity er WHERE er.expenseId = :expenseId " +
                    "ORDER BY er.editedAt DESC"; // ensure latest revisions come first
            Map<String, Object> params = new HashMap<>();
            params.put("expenseId", expenseId.toString());
            return postgresClient.query(jpql, params, ExpenseRevisionEntity.class);
        } catch (Exception e) {
            log.error("[ExpenseDao : findRevisions] : error fetching revisions for expense {}", expenseId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExpenseEntity> findByGroupId(UUID groupId) {
        log.debug("[ExpenseDao : findByGroupId] : {}", groupId);
        try {
            // Since we are using EAGER fetch
            String jpql = "SELECT e FROM ExpenseEntity e " +
                    "JOIN FETCH e.group g " +
                    "WHERE g.groupId = :groupId";
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            return postgresClient.query(jpql, params, ExpenseEntity.class);
        } catch (Exception e) {
            log.error("[ExpenseDao : findByGroupId] : error fetching expenses for group {}", groupId, e);
            throw new RuntimeException(e);
        }
    }
}
