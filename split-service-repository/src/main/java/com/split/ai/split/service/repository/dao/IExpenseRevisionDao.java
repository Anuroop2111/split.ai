package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;

public interface IExpenseRevisionDao {

    void saveRevision(ExpenseRevisionEntity entity);

    void updateRevision(ExpenseRevisionEntity entity);
}
