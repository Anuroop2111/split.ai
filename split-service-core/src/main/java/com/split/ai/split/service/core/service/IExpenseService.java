package com.split.ai.split.service.core.service;

import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;

public interface IExpenseService {
    ExpenseResponse getExpense(String expenseId);

    void createExpense(CreateExpenseRequest request);

    void updateExpense(UpdateExpenseRequest request);

    void deleteExpense(DeleteExpenseRequest request);

    ExpenseHistoryResponse getHistory(String expenseId);
}
