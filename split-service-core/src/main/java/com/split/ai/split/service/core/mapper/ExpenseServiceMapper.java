package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.enums.EXPENSE_REVISION_STATUS;
import com.split.ai.split.service.model.enums.EXPENSE_STATUS;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.UserExpenseData;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.model.response.user.UserExpenseDto;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.ExpenseRevisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface ExpenseServiceMapper extends BaseServiceMapper {

    ExpenseServiceMapper MAPPER = Mappers.getMapper(ExpenseServiceMapper.class);

    @Mapping(target = "expenseId", source = "expenseId")
    @Mapping(target = "group", source = "request.groupId", qualifiedByName = "getGroup")
    @Mapping(target = "groupId", source = "request.groupId")
    @Mapping(target = "currentRevision", source = "revision")
    @Mapping(target = "currentRevisionId", source = "revision.expenseRevisionId")
    @Mapping(target = "expenseStatus", source = "request", qualifiedByName = "pendingExpenseStatus")
    ExpenseEntity toExpenseEntity(UUID expenseId, CreateExpenseRequest request, ExpenseRevisionEntity revision);

    @Mapping(target = "expenseRevisionId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "editedByUser", source = "request.userId", qualifiedByName = "getUser")
    @Mapping(target = "editedUserId", source = "request.userId")
    @Mapping(target = "createdAt", source = "request", qualifiedByName = "currentEpochTime")
    @Mapping(target = "payer", source = "request.payerId", qualifiedByName = "getUser")
    @Mapping(target = "payerId", source = "request.payerId")
    @Mapping(target = "amount", source = "request.amount")
    @Mapping(target = "expenseDate", source = "request.expenseDate")
    @Mapping(target = "splitMode", source = "request.splitMode")
    @Mapping(target = "currency", source = "request.currency")
    @Mapping(target = "category", source = "request.category")
    @Mapping(target = "subCategory", source = "request.subCategory")
    @Mapping(target = "revisionStatus", source = "request", qualifiedByName = "activeRevisionStatus")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "userShares", source = "request.userExpenseDetails", qualifiedByName = "mapUserExpenseDto")
    @Mapping(target = "metaData", source = "request.metaData")
    ExpenseRevisionEntity toRevisionEntity(CreateExpenseRequest request, UUID expenseId);

    @Mapping(target = "expenseId", source = "expense.expenseId")
    @Mapping(target = "groupId", source = "expense.groupId")
    @Mapping(target = "payerId", source = "currentRevision.payerId")
    @Mapping(target = "amount", source = "currentRevision.amount")
    @Mapping(target = "expenseDate", source = "currentRevision.expenseDate")
    @Mapping(target = "description", source = "currentRevision.description")
    @Mapping(target = "splitMode", source = "currentRevision.splitMode")
    @Mapping(target = "currency", source = "currentRevision.currency")
    @Mapping(target = "category", source = "currentRevision.category")
    @Mapping(target = "subCategory", source = "currentRevision.subCategory")
    @Mapping(target = "expenseStatus", source = "expense.expenseStatus")
    @Mapping(target = "createdAt", source = "expense.createdAt")
    @Mapping(target = "userExpenseDetails", source = "currentRevision.userShares", qualifiedByName = "mapUserSharesToDtos")
    ExpenseResponse toExpenseResponse(ExpenseEntity expense);

    @Named("pendingExpenseStatus")
    default EXPENSE_STATUS pendingExpenseStatus(Object src) {
        return EXPENSE_STATUS.PENDING;
    }

    @Named("activeRevisionStatus")
    default EXPENSE_REVISION_STATUS activeRevisionStatus(Object src) {
        return EXPENSE_REVISION_STATUS.ACTIVE;
    }

    @Named("cancelledExpenseStatus")
    default EXPENSE_STATUS cancelledExpenseStatus(Object src) {
        return EXPENSE_STATUS.CANCELLED;
    }

    @Named("inActiveExpenseRevisionStatus")
    default EXPENSE_REVISION_STATUS inActiveExpenseRevisionStatus(Object src) {
        return EXPENSE_REVISION_STATUS.IN_ACTIVE;
    }

    @Named("mapUserExpenseDto")
    default Map<UUID, BigDecimal> mapUserExpenseDto(List<UserExpenseDto> details) {
        if (details == null) {
            return new HashMap<>();
        }
        return details.stream().collect(Collectors.toMap(UserExpenseDto::getUserId, UserExpenseDto::getSharedAmount));
    }

    @Named("mapUserExpenseData")
    default Map<UUID, BigDecimal> mapUserExpenseData(List<UserExpenseData> details) {
        if (details == null) {
            return new HashMap<>();
        }
        return details.stream().collect(Collectors.toMap(UserExpenseData::getUserId, UserExpenseData::getSharedAmount));
    }

    @Named("mapUserSharesToDtos")
    default List<UserExpenseDto> mapUserSharesToDtos(Map<UUID, BigDecimal> shares) {
        if (shares == null) {
            return null;
        }
        return shares.entrySet().stream()
                .map(e -> UserExpenseDto.builder()
                        .userId(e.getKey())
                        .sharedAmount(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
