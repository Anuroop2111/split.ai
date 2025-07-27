package com.split.ai.split.service.core.service;

import com.split.ai.split.service.core.service.impl.IGroupService;
import com.split.ai.split.service.model.request.*;
import com.split.ai.split.service.model.response.GroupExpenseResponse;
import com.split.ai.split.service.model.response.GroupUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service handling group operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService implements IGroupService {
    @Override
    public GroupExpenseResponse getGroupDetails(UUID groupId) {
        return new GroupExpenseResponse();
    }

    @Override
    public void createGroup(CreateGroupRequest request) {
        // no-op
    }

    @Override
    public void addUserToGroup(AddUserToGroupRequest request) {
        // no-op
    }

    @Override
    public void removeUser(UUID groupId, RemoveUserFromGroupRequest request) {
        // no-op
    }

    @Override
    public void toggleSettleMode(ToggleSettleModeRequest request) {
        // no-op
    }

    @Override
    public void leaveGroup(LeaveGroupRequest request) {
        // no-op
    }

    @Override
    public void deleteGroup(DeleteGroupRequest request) {
        // no-op
    }

    @Override
    public void updateGroup(UpdateGroupRequest request) {
        // no-op
    }

    @Override
    public void inviteMember(UUID groupId, InviteMembersToGroupRequest request) {
        // no-op
    }

    @Override
    public GroupUserResponse getMembers(UUID groupId) {
        return new GroupUserResponse();
    }
}
