package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.service.IExpenseService;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service handling expense operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService implements IExpenseService {

    @Override
    public ExpenseResponse getExpense(UUID expenseId) {
        return new ExpenseResponse();
    }

    @Override
    public void createExpense(CreateExpenseRequest request) {
        // no-op
    }

    @Override
    public void updateExpense(UpdateExpenseRequest request) {
        // no-op
    }

    @Override
    public void deleteExpense(DeleteExpenseRequest request) {
        // no-op
    }

    @Override
    public ExpenseHistoryResponse getHistory(UUID expenseId) {
        return new ExpenseHistoryResponse();
    }
}
