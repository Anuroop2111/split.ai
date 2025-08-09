package com.split.ai.split.service.core.helper;

import com.split.ai.split.service.core.mapper.ExpenseServiceMapper;
import com.split.ai.split.service.model.enums.EXPENSE_REVISION_STATUS;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import com.split.ai.split.service.repository.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpenseHelper {

    public static Pair<Boolean, ExpenseRevisionEntity> getUpdatedExpenseRevision(UpdateExpenseRequest request, ExpenseRevisionEntity currentRevision) {
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
                .revisionStatus(EXPENSE_REVISION_STATUS.ACTIVE)
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
}
