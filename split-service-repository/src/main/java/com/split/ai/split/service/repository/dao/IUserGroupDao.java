package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.model.request.user.UserRoleData;

import java.util.List;
import java.util.UUID;

public interface IUserGroupDao {

    void addUsers(UUID groupId, List<UserRoleData> users);

    void removeUser(UUID groupId, UUID userId);

    List<UserRoleData> findUsersByGroupId(UUID groupId);

    UserRoleData findUserRole(UUID groupId, UUID userId);
}
