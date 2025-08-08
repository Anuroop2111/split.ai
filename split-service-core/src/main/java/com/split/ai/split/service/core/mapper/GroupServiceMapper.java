package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.enums.GroupStatus;
import com.split.ai.split.service.model.enums.GroupType;
import com.split.ai.split.service.model.enums.Role;
import com.split.ai.split.service.model.enums.SettleMode;
import com.split.ai.split.service.model.request.group.CreateGroupRequest;
import com.split.ai.split.service.model.request.group.DeleteGroupRequest;
import com.split.ai.split.service.model.request.group.ToggleGroupSettleMode;
import com.split.ai.split.service.model.request.group.UpdateGroupRequest;
import com.split.ai.split.service.model.request.user.UserRoleData;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;
import com.split.ai.split.service.model.response.user.UserExpenseDto;
import com.split.ai.split.service.model.response.user.UserGroupResponse;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface GroupServiceMapper extends BaseServiceMapper {

    GroupServiceMapper MAPPER = Mappers.getMapper(GroupServiceMapper.class);

    @Mapping(target = "groupId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "groupType", source = "groupType", qualifiedByName = "defaultGroupType")
    @Mapping(target = "currency", source = "baseCurrency")
    @Mapping(target = "settleMode", source = "request", qualifiedByName = "normalSettleMode")
    @Mapping(target = "groupStatus", source = "request", qualifiedByName = "activeGroupStatus")
    GroupEntity convert(CreateGroupRequest request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "settleMode", source = "settleMode")
    GroupEntity convert(ToggleGroupSettleMode request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupStatus", source = "request", qualifiedByName = "deletedGroupStatus")
    GroupEntity convert(DeleteGroupRequest request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupName", source = "newGroupName")
    @Mapping(target = "groupType", source = "newGroupType")
    GroupEntity convert(UpdateGroupRequest request);

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
    @Mapping(target = "userExpenseDetails", source = "expense", qualifiedByName = "mapUserShares")
    ExpenseResponse mapToExpenseResponse(ExpenseEntity expense);

    @Mapping(target = "userGroup", source = "group")
    @Mapping(target = "expenses", source = "expenses")
    GroupExpenseResponse convert(GroupEntity group, List<ExpenseEntity> expenses);

//    UserGroupResponse convert(GroupEntity entity);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupUserData", source = "groupUserData")
    GroupUserResponse convert(UUID groupId, List<UserRoleData> groupUserData);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "role", source = "userId", qualifiedByName = "adminRole")
    UserRoleData createAdmin(UUID userId);

    @Named("defaultGroupType")
    default GroupType defaultGroupType(GroupType groupType) {
        return groupType == null ? GroupType.COMMON : groupType;
    }

    @Named("normalSettleMode")
    default SettleMode normalSettleMode(Object src) {
        return SettleMode.NORMAL_SETTLE;
    }

    @Named("activeGroupStatus")
    default GroupStatus activeGroupStatus(Object src) {
        return GroupStatus.ACTIVE;
    }

    @Named("deletedGroupStatus")
    default GroupStatus deletedGroupStatus(Object src) {
        return GroupStatus.DELETED;
    }

    @Named("adminRole")
    default Role adminRole(Object src) {
        return Role.ADMIN;
    }

    @Named("mapUserShares")
    default List<UserExpenseDto> mapUserShares(ExpenseEntity expense) {
        if (expense.getCurrentRevision() == null ||
                expense.getCurrentRevision().getUserShares() == null) {
            return null;
        }
        return expense.getCurrentRevision().getUserShares().entrySet().stream()
                .map(entry -> UserExpenseDto.builder()
                        .userId(entry.getKey())
                        .sharedAmount(entry.getValue())
                        .build())
                .collect(java.util.stream.Collectors.toList());
    }
}
