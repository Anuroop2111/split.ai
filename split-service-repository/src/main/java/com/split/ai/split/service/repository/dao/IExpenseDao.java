package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;

import java.util.List;
import java.util.UUID;

public interface IExpenseDao {

    void save(ExpenseEntity entity);

    void update(ExpenseEntity entity);

    ExpenseEntity findById(UUID expenseId);

    List<ExpenseRevisionEntity> findRevisions(UUID expenseId);

    List<ExpenseEntity> findByGroupId(UUID groupId);
}
