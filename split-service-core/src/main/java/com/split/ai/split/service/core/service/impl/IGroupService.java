package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.model.request.*;
import com.split.ai.split.service.model.response.GroupExpenseResponse;
import com.split.ai.split.service.model.response.GroupUserResponse;

import java.util.UUID;

public interface IGroupService {
    GroupExpenseResponse getGroupDetails(UUID groupId);

    void createGroup(CreateGroupRequest request);

    void addUserToGroup(AddUserToGroupRequest request);

    void removeUser(UUID groupId, RemoveUserFromGroupRequest request);

    void toggleSettleMode(ToggleSettleModeRequest request);

    void leaveGroup(LeaveGroupRequest request);

    void deleteGroup(DeleteGroupRequest request);

    void updateGroup(UpdateGroupRequest request);

    void inviteMember(UUID groupId, InviteMembersToGroupRequest request);

    GroupUserResponse getMembers(UUID groupId);
}
