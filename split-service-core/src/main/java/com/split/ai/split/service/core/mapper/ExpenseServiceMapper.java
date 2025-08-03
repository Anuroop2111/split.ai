package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.request.expense.CreateExpenseRequest;
import com.split.ai.split.service.model.request.expense.DeleteExpenseRequest;
import com.split.ai.split.service.model.request.expense.UpdateExpenseRequest;
import com.split.ai.split.service.model.request.expense.UserExpenseData;
import com.split.ai.split.service.model.response.expense.ExpenseEditDto;
import com.split.ai.split.service.model.response.expense.ExpenseHistoryResponse;
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

    @Mapping(target = "expenseRevisionId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "expenseId", expression = "java(expenseId.toString())")
    @Mapping(target = "editedUserId", source = "payerId")
    @Mapping(target = "payerId", source = "payerId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "expenseDate", expression = "java(System.currentTimeMillis())")
    @Mapping(target = "splitMode", source = "splitMode")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "subCategory", source = "subCategory")
    @Mapping(target = "expenseStatus", expression = "java(com.split.ai.split.service.model.enums.ExpenseStatus.PENDING)")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "userShares", source = "userExpenseDetails", qualifiedByName = "mapUserExpenseDto")
    ExpenseRevisionEntity toRevisionEntity(CreateExpenseRequest request, UUID expenseId);

    @Mapping(target = "expenseId", source = "expenseId")
    @Mapping(target = "groupId", source = "request.groupId")
    @Mapping(target = "currentRevision", source = "revision")
    ExpenseEntity toExpenseEntity(UUID expenseId, CreateExpenseRequest request, ExpenseRevisionEntity revision);

    @Mapping(target = "expenseRevisionId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "expenseId", expression = "java(request.getExpenseId().toString())")
    @Mapping(target = "editedUserId", source = "editedBy")
    @Mapping(target = "payerId", source = "payer")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "expenseDate", source = "expenseDate")
    @Mapping(target = "splitMode", source = "splitMode")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "subCategory", source = "subCategory")
    @Mapping(target = "expenseStatus", expression = "java(com.split.ai.split.service.model.enums.ExpenseStatus.PENDING)")
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
    @Mapping(target = "expenseStatus", expression = "java(com.split.ai.split.service.model.enums.ExpenseStatus.CANCELLED)")
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

    @Mapping(target = "expenseEditList", source = "revisions")
    ExpenseHistoryResponse toHistoryResponse(List<ExpenseRevisionEntity> revisions);

    @Mapping(target = "editedBy", source = "editedUserId")
    @Mapping(target = "payerNew", source = "payerId")
    @Mapping(target = "amountNew", source = "amount")
    @Mapping(target = "expenseDateNew", source = "expenseDate")
    @Mapping(target = "splitModeNew", source = "splitMode")
    @Mapping(target = "currencyNew", source = "currency")
    @Mapping(target = "categoryNew", source = "category")
    @Mapping(target = "subCategoryNew", source = "subCategory")
    @Mapping(target = "descriptionNew", source = "description")
    @Mapping(target = "metaDataNew", source = "metaData")
    @Mapping(target = "userSharesNew", source = "userShares")
    ExpenseEditDto toExpenseEditDto(ExpenseRevisionEntity entity);

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
