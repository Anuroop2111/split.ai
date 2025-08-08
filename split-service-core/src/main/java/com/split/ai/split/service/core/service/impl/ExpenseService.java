package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.ExpenseServiceMapper;
import com.split.ai.split.service.core.service.IExpenseService;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ChangeDto;
import com.split.ai.split.service.model.response.expense.ExpenseEditDto;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import com.split.ai.split.service.model.enums.ExpenseRevisionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        return ExpenseServiceMapper.MAPPER.toExpenseResponse(entity);
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

        ExpenseEntity existing = expenseDao.findById(request.getExpenseId());
        if (existing == null) {
            return;
        }

        ExpenseRevisionEntity current = existing.getCurrentRevision();
        ExpenseRevisionEntity.ExpenseRevisionEntityBuilder builder = ExpenseRevisionEntity.builder()
                .expenseRevisionId(UUID.randomUUID())
                .expenseId(current.getExpenseId())
                .editedUserId(request.getEditedBy())
                .revisionStatus(ExpenseRevisionStatus.ACTIVE)
                .payerId(current.getPayerId())
                .amount(current.getAmount())
                .expenseDate(current.getExpenseDate())
                .splitMode(current.getSplitMode())
                .currency(current.getCurrency())
                .category(current.getCategory())
                .subCategory(current.getSubCategory())
                .description(current.getDescription())
                .metaData(current.getMetaData())
                .userShares(current.getUserShares());

        boolean updated = false;

        if (request.getPayerId() != null && !Objects.equals(request.getPayerId(), current.getPayerId())) {
            builder.payerId(request.getPayerId());
            updated = true;
        }
        if (request.getAmount() != null && current.getAmount().compareTo(request.getAmount()) != 0) {
            builder.amount(request.getAmount());
            updated = true;
        }
        if (request.getExpenseDate() != null && !Objects.equals(request.getExpenseDate(), current.getExpenseDate())) {
            builder.expenseDate(request.getExpenseDate());
            updated = true;
        }
        if (request.getSplitMode() != null && request.getSplitMode() != current.getSplitMode()) {
            builder.splitMode(request.getSplitMode());
            updated = true;
        }
        if (request.getCurrency() != null && request.getCurrency() != current.getCurrency()) {
            builder.currency(request.getCurrency());
            updated = true;
        }
        if (request.getCategory() != null && request.getCategory() != current.getCategory()) {
            builder.category(request.getCategory());
            updated = true;
        }
        if (request.getSubCategory() != null && request.getSubCategory() != current.getSubCategory()) {
            builder.subCategory(request.getSubCategory());
            updated = true;
        }
        if (request.getDescription() != null && !Objects.equals(request.getDescription(), current.getDescription())) {
            builder.description(request.getDescription());
            updated = true;
        }
        if (request.getMetaData() != null && !Objects.equals(request.getMetaData(), current.getMetaData())) {
            builder.metaData(request.getMetaData());
            updated = true;
        }
        if (request.getUserExpenseDetails() != null) {
            Map<UUID, BigDecimal> shares = ExpenseServiceMapper.MAPPER.mapUserExpenseData(request.getUserExpenseDetails());
            if (!Objects.equals(shares, current.getUserShares())) {
                builder.userShares(shares);
                updated = true;
            }
        }

        if (!updated) {
            throw new IllegalArgumentException("Invalid Expense Update Request");
        }

        ExpenseRevisionEntity newRevision = builder.build();

        current.setRevisionStatus(ExpenseRevisionStatus.IN_ACTIVE);
        expenseDao.saveRevision(current);

        expenseDao.saveRevision(newRevision);
        ExpenseEntity entity = ExpenseServiceMapper.MAPPER.toExpenseEntity(request.getExpenseId(), newRevision);
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
        List<ExpenseRevisionEntity> revisions = expenseDao.findRevisions(expenseId);
        List<ExpenseEditDto> edits = new ArrayList<>();

        for (int i = 0; i < revisions.size(); i++) {
            ExpenseRevisionEntity current = revisions.get(i);
            ExpenseRevisionEntity previous = i + 1 < revisions.size() ? revisions.get(i + 1) : null;

            Map<String, ChangeDto> changes = new HashMap<>();

            computeChange("payer", previous == null ? null : previous.getPayerId(), current.getPayerId(), changes);
            computeChange("amount", previous == null ? null : previous.getAmount(), current.getAmount(), changes);
            computeChange("expenseDate", previous == null ? null : previous.getExpenseDate(), current.getExpenseDate(), changes);
            computeChange("splitMode", previous == null ? null : previous.getSplitMode(), current.getSplitMode(), changes);
            computeChange("currency", previous == null ? null : previous.getCurrency(), current.getCurrency(), changes);
            computeChange("category", previous == null ? null : previous.getCategory(), current.getCategory(), changes);
            computeChange("subCategory", previous == null ? null : previous.getSubCategory(), current.getSubCategory(), changes);
            computeChange("description", previous == null ? null : previous.getDescription(), current.getDescription(), changes);
            computeChange("metaData", previous == null ? null : previous.getMetaData(), current.getMetaData(), changes);

            ExpenseEditDto dto = ExpenseEditDto.builder()
                    .editedBy(current.getEditedUserId())
                    .editedAt(current.getEditedAt())
                    .changes(changes.isEmpty() ? null : changes)
                    .userSharesOld(previous == null ? null : previous.getUserShares())
                    .userSharesNew(current.getUserShares())
                    .build();
            edits.add(dto);
        }

        return ExpenseHistoryResponse.builder()
                .expenseEditList(edits)
                .build();
    }

    private void computeChange(String key, Object oldVal, Object newVal, Map<String, ChangeDto> changes) {
        if (!Objects.equals(oldVal, newVal)) {
            String oldStr = oldVal == null ? null : convertToString(oldVal);
            String newStr = newVal == null ? null : convertToString(newVal);
            changes.put(key, ChangeDto.builder().oldValue(oldStr).newValue(newStr).build());
        }
    }

    private String convertToString(Object value) {
        if (value instanceof BigDecimal bd) {
            return bd.toPlainString();
        }
        return value.toString();
    }
}
