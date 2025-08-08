package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.ExpenseServiceMapper;
import com.split.ai.split.service.core.service.IExpenseService;
import com.split.ai.split.service.model.enums.ExpenseRevisionStatus;
import com.split.ai.split.service.model.enums.ExpenseStatus;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.response.expense.ChangeDto;
import com.split.ai.split.service.model.response.expense.ExpenseEditDto;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.dao.IExpenseRevisionDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import com.split.ai.split.service.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
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
    private final IExpenseRevisionDao expenseRevisionDao;

    @Override
    public ExpenseResponse getExpense(UUID expenseId) {
        log.info("[ExpenseService : getExpense] : {}", expenseId);
        ExpenseEntity entity = expenseDao.findById(expenseId);
        return ExpenseServiceMapper.MAPPER.toExpenseResponse(entity);
    }

    @Override
    public void createExpense(CreateExpenseRequest request) {
        log.info("[ExpenseService : createExpense] : {}", request);
        // todo: SplitEngine logic to verify the userExpenseDetails

        UUID expenseId = UUID.randomUUID();
        ExpenseRevisionEntity revision = ExpenseServiceMapper.MAPPER.toRevisionEntity(request, expenseId);
        expenseRevisionDao.saveRevision(revision);
        ExpenseEntity entity = ExpenseServiceMapper.MAPPER.toExpenseEntity(expenseId, request, revision);
        expenseDao.save(entity);
    }

    @Override
    public void updateExpense(UpdateExpenseRequest request) {
        log.info("[ExpenseService : updateExpense] : {}", request);

        ExpenseEntity existing = expenseDao.findById(request.getExpenseId());
        if (existing == null) {
            throw new IllegalArgumentException("[ExpenseService : updateExpense] Expense not found for expenseId: {}");
        }

        ExpenseRevisionEntity currentRevision = existing.getCurrentRevision();
        Pair<Boolean, ExpenseRevisionEntity> updatedFlagAndUpdatedExpenseRevision = getUpdatedExpenseRevision(request, currentRevision);
        Boolean updated = updatedFlagAndUpdatedExpenseRevision.getLeft();
        ExpenseRevisionEntity newRevision = updatedFlagAndUpdatedExpenseRevision.getRight();

        if (!updated) {
            throw new IllegalArgumentException("[ExpenseService : updateExpense] Invalid Expense Update Request");
        }

        currentRevision.setRevisionStatus(ExpenseRevisionStatus.IN_ACTIVE);
        expenseRevisionDao.updateRevision(currentRevision);
        expenseRevisionDao.saveRevision(newRevision);
        existing.setCurrentRevision(newRevision);
        existing.setCurrentRevisionId(newRevision.getExpenseRevisionId());
        expenseDao.update(existing);
    }

    private Pair<Boolean, ExpenseRevisionEntity> getUpdatedExpenseRevision(UpdateExpenseRequest request, ExpenseRevisionEntity currentRevision) {
        ExpenseRevisionEntity.ExpenseRevisionEntityBuilder builder = ExpenseRevisionEntity.builder()
                .expenseRevisionId(UUID.randomUUID())
                .expenseId(currentRevision.getExpenseId())
                .editedByUser(UserEntity.builder().userId(request.getEditedBy()).build())
                .editedUserId(request.getEditedBy())
                .payer(UserEntity.builder().userId(currentRevision.getPayerId()).build())
                .payerId(currentRevision.getPayerId())
                .amount(currentRevision.getAmount())
                .expenseDate(currentRevision.getExpenseDate())
                .splitMode(currentRevision.getSplitMode())
                .currency(currentRevision.getCurrency())
                .category(currentRevision.getCategory())
                .subCategory(currentRevision.getSubCategory())
                .revisionStatus(ExpenseRevisionStatus.ACTIVE)
                .description(currentRevision.getDescription())
                .metaData(currentRevision.getMetaData())
                .userShares(currentRevision.getUserShares());

        boolean updated = false;

        if (request.getPayerId() != null && !Objects.equals(request.getPayerId(), currentRevision.getPayerId())) {
            builder.payerId(request.getPayerId());
            updated = true;
        }
        if (request.getAmount() != null && currentRevision.getAmount().compareTo(request.getAmount()) != 0) {
            builder.amount(request.getAmount());
            updated = true;
        }
        if (request.getExpenseDate() != null && !Objects.equals(request.getExpenseDate(), currentRevision.getExpenseDate())) {
            builder.expenseDate(request.getExpenseDate());
            updated = true;
        }
        if (request.getSplitMode() != null && request.getSplitMode() != currentRevision.getSplitMode()) {
            builder.splitMode(request.getSplitMode());
            updated = true;
        }
        if (request.getCurrency() != null && request.getCurrency() != currentRevision.getCurrency()) {
            builder.currency(request.getCurrency());
            updated = true;
        }
        if (request.getCategory() != null && request.getCategory() != currentRevision.getCategory()) {
            builder.category(request.getCategory());
            updated = true;
        }
        if (request.getSubCategory() != null && request.getSubCategory() != currentRevision.getSubCategory()) {
            builder.subCategory(request.getSubCategory());
            updated = true;
        }
        if (request.getDescription() != null && !Objects.equals(request.getDescription(), currentRevision.getDescription())) {
            builder.description(request.getDescription());
            updated = true;
        }
        if (request.getMetaData() != null && !Objects.equals(request.getMetaData(), currentRevision.getMetaData())) {
            builder.metaData(request.getMetaData());
            updated = true;
        }
        if (request.getUserExpenseDetails() != null) {
            Map<UUID, BigDecimal> shares = ExpenseServiceMapper.MAPPER.mapUserExpenseData(request.getUserExpenseDetails());
            if (!Objects.equals(shares, currentRevision.getUserShares())) {
                builder.userShares(shares);
                updated = true;
            }
        }
        ExpenseRevisionEntity newRevision = builder.build();
        return Pair.of(updated, newRevision);
    }

    @Override
    public void deleteExpense(DeleteExpenseRequest request) {
        log.info("[ExpenseService : deleteExpense] : {}", request);
        ExpenseEntity existing = expenseDao.findById(request.getExpenseId());
        if (existing == null) {
            return;
        }
        ExpenseRevisionEntity currentRevision = existing.getCurrentRevision();
        currentRevision.setRevisionStatus(ExpenseRevisionStatus.IN_ACTIVE);
        expenseRevisionDao.updateRevision(currentRevision);
        existing.setExpenseStatus(ExpenseStatus.CANCELLED);
        existing.setCurrentRevision(currentRevision);
        expenseDao.update(existing);
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
                    .editedAt(current.getCreatedAt())
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
