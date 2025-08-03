package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.service.IGroupService;
import com.split.ai.split.service.model.enums.GroupStatus;
import com.split.ai.split.service.model.enums.GroupType;
import com.split.ai.split.service.model.enums.Role;
import com.split.ai.split.service.model.enums.SettleMode;
import com.split.ai.split.service.model.request.group.AddUserToGroupRequest;
import com.split.ai.split.service.model.request.group.CreateGroupRequest;
import com.split.ai.split.service.model.request.group.DeleteGroupRequest;
import com.split.ai.split.service.model.request.group.InviteMembersToGroupRequest;
import com.split.ai.split.service.model.request.group.LeaveGroupRequest;
import com.split.ai.split.service.model.request.group.RemoveUserFromGroupRequest;
import com.split.ai.split.service.model.request.group.ToggleGroupSettleMode;
import com.split.ai.split.service.model.request.group.UpdateGroupRequest;
import com.split.ai.split.service.model.request.user.UserRoleData;
import com.split.ai.split.service.model.response.expense.ExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;
import com.split.ai.split.service.model.response.user.UserGroupResponse;
import com.split.ai.split.service.repository.dao.IExpenseDao;
import com.split.ai.split.service.repository.dao.IGroupDao;
import com.split.ai.split.service.repository.dao.IUserGroupDao;
import com.split.ai.split.service.repository.entity.ExpenseEntity;
import com.split.ai.split.service.repository.entity.GroupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service handling group operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService implements IGroupService {

    private final IGroupDao groupDao;
    private final IUserGroupDao userGroupDao;
    private final IExpenseDao expenseDao;

    @Override
    public GroupExpenseResponse getGroupDetails(UUID groupId) {
        log.info("[GroupService : getGroupDetails] : {}", groupId);
        GroupEntity group = groupDao.findById(groupId);
        if (group == null) {
            return GroupExpenseResponse.builder().build();
        }
        UserGroupResponse userGroup = UserGroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupType(group.getGroupType())
                .settleMode(group.getSettleMode())
                .build();
        List<UserRoleData> members = userGroupDao.findUsersByGroupId(groupId);
        List<ExpenseEntity> expenseEntities = expenseDao.findByGroupId(groupId);
        List<ExpenseResponse> expenses = expenseEntities.stream()
                .map(e -> ExpenseResponse.builder()
                        .expenseId(e.getExpenseId())
                        .groupId(groupId)
                        .build())
                .collect(Collectors.toList());
        return GroupExpenseResponse.builder()
                .userGroup(userGroup)
                .expenses(expenses)
                .build();
    }

    @Override
    public void createGroup(CreateGroupRequest request) {
        log.info("[GroupService : createGroup] : {}", request);
        GroupEntity entity = GroupEntity.builder()
                .groupId(UUID.randomUUID())
                .groupName(request.getGroupName())
                .groupType(request.getGroupType() == null ? GroupType.COMMON : request.getGroupType())
                .currency(request.getBaseCurrency())
                .settleMode(SettleMode.NORMAL_SETTLE)
                .groupStatus(GroupStatus.ACTIVE)
                .build();
        groupDao.save(entity);
        List<UserRoleData> members = new ArrayList<>();
        members.add(UserRoleData.builder()
                .userId(request.getUserInitiated())
                .role(Role.ADMIN)
                .build());
        if (request.getAdditionalUserData() != null) {
            members.addAll(request.getAdditionalUserData());
        }
        userGroupDao.addUsers(entity.getGroupId(), members);
    }

    @Override
    public void addUserToGroup(AddUserToGroupRequest request) {
        log.info("[GroupService : addUserToGroup] : {}", request);
        userGroupDao.addUsers(request.getGroupId(), request.getAdditionalUserData());
    }

    @Override
    public void removeUser(UUID groupId, RemoveUserFromGroupRequest request) {
        log.info("[GroupService : removeUser] : group {} user {}", groupId, request.getUserToRemove());
        userGroupDao.removeUser(groupId, request.getUserToRemove());
    }

    @Override
    public void toggleSettleMode(ToggleGroupSettleMode request) {
        log.info("[GroupService : toggleSettleMode] : {}", request);
        GroupEntity entity = GroupEntity.builder()
                .groupId(request.getGroupId())
                .settleMode(request.getSettleMode())
                .build();
        groupDao.update(entity);
    }

    @Override
    public void leaveGroup(LeaveGroupRequest request) {
        log.info("[GroupService : leaveGroup] : {}", request);
        userGroupDao.removeUser(request.getGroupId(), request.getUserId());
    }

    @Override
    public void deleteGroup(DeleteGroupRequest request) {
        log.info("[GroupService : deleteGroup] : {}", request);
        GroupEntity entity = GroupEntity.builder()
                .groupId(request.getGroupId())
                .groupStatus(GroupStatus.DELETED)
                .build();
        groupDao.update(entity);
    }

    @Override
    public void updateGroup(UpdateGroupRequest request) {
        log.info("[GroupService : updateGroup] : {}", request);
        GroupEntity entity = GroupEntity.builder()
                .groupId(request.getGroupId())
                .build();
        if (request.getNewGroupName() != null) {
            entity.setGroupName(request.getNewGroupName());
        }
        if (request.getNewGrouptType() != null) {
            entity.setGroupType(request.getNewGrouptType());
        }
        groupDao.update(entity);
    }

    @Override
    public void inviteMember(UUID groupId, InviteMembersToGroupRequest request) {
        log.info("[GroupService : inviteMember] : group {} invite {}", groupId, request);
        // Invitation logic to be implemented later
    }

    @Override
    public GroupUserResponse getMembers(UUID groupId) {
        log.info("[GroupService : getMembers] : {}", groupId);
        List<UserRoleData> members = userGroupDao.findUsersByGroupId(groupId);
        return GroupUserResponse.builder()
                .groupId(groupId)
                .groupUserData(members)
                .build();
    }
}
