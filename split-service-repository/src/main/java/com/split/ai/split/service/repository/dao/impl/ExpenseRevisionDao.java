package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.repository.dao.IExpenseRevisionDao;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Data access object for {@link com.split.ai.split.service.repository.entity.ExpenseRevisionEntity}.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ExpenseRevisionDao implements IExpenseRevisionDao {

    private final PostgresClient postgresClient;

    @Override
    public void saveRevision(ExpenseRevisionEntity entity) {
        log.debug("[ExpenseRevisionDao : saveRevision] : {}", entity);
        try {
            entity.beforeInsert();
            postgresClient.insert(entity);
        } catch (Exception e) {
            log.error("[ExpenseRevisionDao : saveRevision] : error saving revision for expenseId {}, expenseRevisionId: {}", entity.getExpenseId(), entity.getExpenseRevisionId(), e);
            throw new RuntimeException(e);
        }
    }

    // Used for setting it IN_ACTIVE
    @Override
    public void updateRevision(ExpenseRevisionEntity entity) {
        log.debug("[ExpenseRevisionDao : updateRevision] : {}", entity);
        try {
            postgresClient.partialUpdate(entity);
        } catch (Exception e) {
            log.error("[ExpenseRevisionDao : saveRevision] : error updating revision for expenseId {}, expenseRevisionId: {}", entity.getExpenseId(), entity.getExpenseRevisionId(), e);
            throw new RuntimeException(e);
        }
    }
}
