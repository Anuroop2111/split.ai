package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.model.request.*;
import com.split.ai.split.service.model.response.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.ExpenseResponse;

public interface IExpenseService {
    ExpenseResponse getExpense(String expenseId);

    void createExpense(CreateExpenseRequest request);

    void updateExpense(UpdateExpenseRequest request);

    void deleteExpense(DeleteExpenseRequest request);

    ExpenseHistoryResponse getHistory(String expenseId);
}
