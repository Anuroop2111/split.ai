package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.ExpenseServiceMapper;
import com.split.ai.split.service.core.service.IExpenseService;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service handling expense operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService implements IExpenseService {

    private final IExpenseDao expenseDao;

    @Override
    public ExpenseResponse getExpense(UUID expenseId) {
        log.info("[ExpenseService : getExpense] : {}", expenseId);
        ExpenseEntity entity = expenseDao.findById(expenseId);
        return entity == null ? null : ExpenseServiceMapper.MAPPER.toExpenseResponse(entity);
    }

    @Override
    public void createExpense(CreateExpenseRequest request) {
        log.info("[ExpenseService : createExpense] : {}", request);
        UUID expenseId = UUID.randomUUID();
        ExpenseRevisionEntity revision = ExpenseServiceMapper.MAPPER.toRevisionEntity(request, expenseId);
        expenseDao.saveRevision(revision);
        ExpenseEntity entity = ExpenseServiceMapper.MAPPER.toExpenseEntity(expenseId, request, revision);
        expenseDao.save(entity);
    }

    @Override
    public void updateExpense(UpdateExpenseRequest request) {
        log.info("[ExpenseService : updateExpense] : {}", request);
        ExpenseRevisionEntity revision = ExpenseServiceMapper.MAPPER.toRevisionEntity(request);
        expenseDao.saveRevision(revision);
        ExpenseEntity entity = ExpenseServiceMapper.MAPPER.toExpenseEntity(request.getExpenseId(), revision);
        expenseDao.update(entity);
    }

    @Override
    public void deleteExpense(DeleteExpenseRequest request) {
        log.info("[ExpenseService : deleteExpense] : {}", request);
        ExpenseEntity existing = expenseDao.findById(request.getExpenseId());
        if (existing == null) {
            return;
        }
        ExpenseRevisionEntity revision = ExpenseServiceMapper.MAPPER.toDeleteRevision(existing.getCurrentRevision(), request);
        expenseDao.saveRevision(revision);
        ExpenseEntity entity = ExpenseServiceMapper.MAPPER.toExpenseEntity(request.getExpenseId(), revision);
        expenseDao.update(entity);
    }

    @Override
    public ExpenseHistoryResponse getHistory(UUID expenseId) {
        log.info("[ExpenseService : getHistory] : {}", expenseId);
        List<ExpenseRevisionEntity> expenseRevisionEntityList =  expenseDao.findRevisions(expenseId);
        return ExpenseHistoryResponse.builder()
                .expenseEditList(expenseRevisionEntityList.stream()
                        .map(ExpenseServiceMapper.MAPPER::toExpenseEditDto)
                        .toList())
                .build();
    }
}
