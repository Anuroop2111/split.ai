package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.GroupServiceMapper;
import com.split.ai.split.service.core.service.IGroupService;
import com.split.ai.split.service.model.request.group.AddUserToGroupRequest;
import com.split.ai.split.service.model.request.group.CreateGroupRequest;
import com.split.ai.split.service.model.request.group.DeleteGroupRequest;
import com.split.ai.split.service.model.request.group.InviteMembersToGroupRequest;
import com.split.ai.split.service.model.request.group.LeaveGroupRequest;
import com.split.ai.split.service.model.request.group.RemoveUserFromGroupRequest;
import com.split.ai.split.service.model.request.group.ToggleGroupSettleMode;
import com.split.ai.split.service.model.request.group.UpdateGroupRequest;
import com.split.ai.split.service.model.enums.Role;
import com.split.ai.split.service.model.request.user.UserRoleData;
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;
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
            return null;
        }
        List<ExpenseEntity> expenseEntities = expenseDao.findByGroupId(groupId);
        return GroupServiceMapper.MAPPER.convert(group, expenseEntities);
    }

    @Override
    public void createGroup(CreateGroupRequest request) {
        log.info("[GroupService : createGroup] : {}", request);
        GroupEntity entity = GroupServiceMapper.MAPPER.convert(request);
        groupDao.save(entity);
        List<UserRoleData> members = new ArrayList<>();
        members.add(GroupServiceMapper.MAPPER.createAdmin(request.getUserInitiated()));
        if (request.getAdditionalUserData() != null) {
            members.addAll(request.getAdditionalUserData());
        }
        userGroupDao.addUsers(entity.getGroupId(), members);
    }

    @Override
    public void addUserToGroup(UUID groupId, AddUserToGroupRequest request) {
        log.info("[GroupService : addUserToGroup] : {}", request);
        validateAdmin(groupId, request.getUserInitiated());
        userGroupDao.addUsers(groupId, request.getAdditionalUserData());
    }

    @Override
    public void removeUser(UUID groupId, RemoveUserFromGroupRequest request) {
        log.info("[GroupService : removeUser] : group {} user {}", groupId, request.getUserToRemove());
        validateAdmin(groupId, request.getUserInitiated());
        userGroupDao.removeUser(groupId, request.getUserToRemove());
    }

    @Override
    public void toggleSettleMode(ToggleGroupSettleMode request) {
        log.info("[GroupService : toggleSettleMode] : {}", request);
        GroupEntity entity = GroupServiceMapper.MAPPER.convert(request);
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
        validateAdmin(request.getGroupId(), request.getUserInitiated());
        GroupEntity entity = GroupServiceMapper.MAPPER.convert(request);
        groupDao.update(entity);
    }

    @Override
    public void updateGroup(UpdateGroupRequest request) {
        log.info("[GroupService : updateGroup] : {}", request);
        GroupEntity entity = GroupServiceMapper.MAPPER.convert(request);
        groupDao.update(entity);
    }

    @Override
    public void inviteMember(UUID groupId, InviteMembersToGroupRequest request) {
        log.info("[GroupService : inviteMember] : group {} invite {}", groupId, request);
        // todo: Invitation logic to be implemented later
    }

    @Override
    public GroupUserResponse getMembers(UUID groupId) {
        log.info("[GroupService : getMembers] : {}", groupId);
        List<UserRoleData> members = userGroupDao.findUsersByGroupId(groupId);
        return GroupServiceMapper.MAPPER.convert(groupId, members);
    }

    private void validateAdmin(UUID groupId, UUID userId) {
        UserRoleData data = userGroupDao.findUserRole(groupId, userId);
        if (data == null || data.getRole() != Role.ADMIN) {
            throw new RuntimeException("User does not have ADMIN role");
        }
    }
}
