package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.ExpenseEntity;

import java.util.List;
import java.util.UUID;

public interface IExpenseDao {

    List<ExpenseEntity> findByGroupId(UUID groupId);
}
