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
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;
import com.split.ai.split.service.model.response.user.UserGroupResponse;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {SettleMode.class, GroupStatus.class, Role.class, GroupType.class})
public interface GroupServiceMapper extends BaseServiceMapper {

    GroupServiceMapper MAPPER = Mappers.getMapper(GroupServiceMapper.class);

    @Mapping(target = "groupId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "groupType", source = "groupType", qualifiedByName = "defaultGroupType")
    @Mapping(target = "currency", source = "baseCurrency")
    @Mapping(target = "settleMode", expression = "java(SettleMode.NORMAL_SETTLE)")
    @Mapping(target = "groupStatus", expression = "java(GroupStatus.ACTIVE)")
    GroupEntity convert(CreateGroupRequest request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "settleMode", source = "settleMode")
    GroupEntity convert(ToggleGroupSettleMode request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupStatus", expression = "java(GroupStatus.DELETED)")
    GroupEntity convert(DeleteGroupRequest request);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupName", source = "newGroupName")
    @Mapping(target = "groupType", source = "newGroupType")
    GroupEntity convert(UpdateGroupRequest request);

    @Mapping(target = "userGroup", source = "group")
    @Mapping(target = "expenses", source = "expenses")
    GroupExpenseResponse convert(GroupEntity group, List<ExpenseEntity> expenses);

    UserGroupResponse convert(GroupEntity entity);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupUserData", source = "groupUserData")
    GroupUserResponse convert(UUID groupId, List<UserRoleData> groupUserData);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "role", expression = "java(Role.ADMIN)")
    UserRoleData createAdmin(UUID userId);

    @Named("defaultGroupType")
    default GroupType defaultGroupType(GroupType groupType) {
        return groupType == null ? GroupType.COMMON : groupType;
    }
}
