package com.split.ai.split.service.core.service;

import com.split.ai.split.service.model.request.group.AddUserToGroupRequest;
import com.split.ai.split.service.model.request.group.CreateGroupRequest;
import com.split.ai.split.service.model.request.group.DeleteGroupRequest;
import com.split.ai.split.service.model.request.group.InviteMembersToGroupRequest;
import com.split.ai.split.service.model.request.group.LeaveGroupRequest;
import com.split.ai.split.service.model.request.group.RemoveUserFromGroupRequest;
import com.split.ai.split.service.model.request.group.ToggleGroupSettleMode;
import com.split.ai.split.service.model.request.group.UpdateGroupRequest;
import com.split.ai.split.service.model.response.group.GroupExpenseResponse;
import com.split.ai.split.service.model.response.group.GroupUserResponse;

import java.util.UUID;

public interface IGroupService {

    GroupExpenseResponse getGroupDetails(UUID groupId);

    void createGroup(CreateGroupRequest request);

    void addUserToGroup(AddUserToGroupRequest request);

    void removeUser(UUID groupId, RemoveUserFromGroupRequest request);

    void toggleSettleMode(ToggleGroupSettleMode request);

    void leaveGroup(LeaveGroupRequest request);

    void deleteGroup(DeleteGroupRequest request);

    void updateGroup(UpdateGroupRequest request);

    void inviteMember(UUID groupId, InviteMembersToGroupRequest request);

    GroupUserResponse getMembers(UUID groupId);
}
