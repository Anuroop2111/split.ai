package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.enums.ExpenseRevisionStatus;
import com.split.ai.split.service.model.enums.ExpenseStatus;
import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
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
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface ExpenseServiceMapper extends BaseServiceMapper {

    ExpenseServiceMapper MAPPER = Mappers.getMapper(ExpenseServiceMapper.class);

    @Mapping(target = "expenseId", source = "expenseId")
    @Mapping(target = "group", source = "request.groupId", qualifiedByName = "getGroup")
    @Mapping(target = "groupId", source = "request.groupId")
    @Mapping(target = "currentRevision", source = "revision")
    @Mapping(target = "expenseStatus", source = "request", qualifiedByName = "pendingExpenseStatus")
    ExpenseEntity toExpenseEntity(UUID expenseId, CreateExpenseRequest request, ExpenseRevisionEntity revision);

    @Mapping(target = "expenseRevisionId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "editedByUser", source = "request.userId", qualifiedByName = "getUser")
    @Mapping(target = "editedUserId", source = "request.userId")
    @Mapping(target = "editedAt", source = "request", qualifiedByName = "currentEpochTime")
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

    @Mapping(target = "expenseRevisionId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "expenseId", source = "request.expenseId", qualifiedByName = "uuidToString")
    @Mapping(target = "editedUserId", source = "editedBy")
    @Mapping(target = "payerId", source = "request.payerId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "expenseDate", source = "expenseDate")
    @Mapping(target = "splitMode", source = "splitMode")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "subCategory", source = "subCategory")
    @Mapping(target = "expenseStatus", source = "request", qualifiedByName = "pendingExpenseStatus")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "metaData", source = "metaData")
    @Mapping(target = "userShares", source = "userExpenseDetails", qualifiedByName = "mapUserExpenseData")
    ExpenseRevisionEntity toRevisionEntity(UpdateExpenseRequest request);

    @Mapping(target = "expenseRevisionId", source = "current", qualifiedByName = "randomUUID")
    @Mapping(target = "expenseId", source = "current.expenseId")
    @Mapping(target = "editedUserId", source = "request.userId")
    @Mapping(target = "payerId", source = "current.payerId")
    @Mapping(target = "amount", source = "current.amount")
    @Mapping(target = "expenseDate", source = "current.expenseDate")
    @Mapping(target = "splitMode", source = "current.splitMode")
    @Mapping(target = "currency", source = "current.currency")
    @Mapping(target = "category", source = "current.category")
    @Mapping(target = "subCategory", source = "current.subCategory")
    @Mapping(target = "expenseStatus", source = "request", qualifiedByName = "cancelledExpenseStatus")
    @Mapping(target = "description", source = "current.description")
    @Mapping(target = "metaData", source = "current.metaData")
    @Mapping(target = "userShares", source = "current.userShares")
    ExpenseRevisionEntity toDeleteRevision(ExpenseRevisionEntity current, DeleteExpenseRequest request);

    @Mapping(target = "expenseId", source = "expenseId")
    @Mapping(target = "currentRevision", source = "revision")
    ExpenseEntity toExpenseEntity(UUID expenseId, ExpenseRevisionEntity revision);

    @Mapping(target = "expenseId", source = "expense.expenseId")
    @Mapping(target = "groupId", source = "expense.groupId")
    @Mapping(target = "payerId", source = "currentRevision.payerId")
    @Mapping(target = "amount", source = "currentRevision.amount")
    @Mapping(target = "description", source = "currentRevision.description")
    @Mapping(target = "splitMode", source = "currentRevision.splitMode")
    @Mapping(target = "currency", source = "currentRevision.currency")
    @Mapping(target = "category", source = "currentRevision.category")
    @Mapping(target = "subCategory", source = "currentRevision.subCategory")
    @Mapping(target = "expenseStatus", source = "currentRevision.expenseStatus")
    @Mapping(target = "createdAt", source = "expense.createdAt")
    @Mapping(target = "userExpenseDetails", source = "currentRevision.userShares", qualifiedByName = "mapUserSharesToDtos")
    ExpenseResponse toExpenseResponse(ExpenseEntity expense);

    @Named("pendingExpenseStatus")
    default ExpenseStatus pendingExpenseStatus(Object src) {
        return ExpenseStatus.PENDING;
    }

    @Named("activeRevisionStatus")
    default ExpenseRevisionStatus activeRevisionStatus(Object src) {
        return ExpenseRevisionStatus.ACTIVE;
    }

    @Named("cancelledExpenseStatus")
    default ExpenseStatus cancelledExpenseStatus(Object src) {
        return ExpenseStatus.CANCELLED;
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
        return details.stream().collect(Collectors.toMap(d -> UUID.fromString(d.getUserId()), UserExpenseData::getSharedAmount));
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
