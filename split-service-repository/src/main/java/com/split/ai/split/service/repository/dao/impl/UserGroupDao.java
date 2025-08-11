package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.commons.postgres.PostgresClient;
import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;
import com.split.ai.split.service.model.request.user.UserRoleData;
import com.split.ai.split.service.repository.dao.IUserGroupDao;
import com.split.ai.split.service.repository.entity.UserGroupEntity;
import com.split.ai.split.service.repository.entity.UserGroupKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Data access object for managing user-group relations.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserGroupDao implements IUserGroupDao {

    private final PostgresClient postgresClient;

    @Override
    public void addUsers(UUID groupId, List<UserRoleData> users) {
        log.debug("[UserGroupDao : addUsers] : group {} users {}", groupId, users);
        if (users == null || users.isEmpty()) {
            return;
        }
        for (UserRoleData user : users) {
            try {
                UserGroupEntity entity = UserGroupEntity.builder()
                        .id(new UserGroupKey(groupId, user.getUserId()))
                        .role(user.getRole())
                        .build();
                entity.beforeInsert();
                postgresClient.insert(entity);
            } catch (Exception e) {
                log.error("[UserGroupDao : addUsers] : error adding user {} to group {}", user.getUserId(), groupId, e);
                throw SplitException.createException(ErrorCode.DATABASE_ERROR);
            }
        }
    }

    @Override
    public void removeUser(UUID groupId, UUID userId) {
        log.debug("[UserGroupDao : removeUser] : group {} user {}", groupId, userId);
        try {
            String sql = "DELETE FROM user_group WHERE group_id = :groupId AND user_id = :userId";
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            params.put("userId", userId);
            postgresClient.queryNative(sql, params, UserGroupEntity.class);
        } catch (Exception e) {
            log.error("[UserGroupDao : removeUser] : error removing user {} from group {}", userId, groupId, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<UserRoleData> findUsersByGroupId(UUID groupId) {
        log.debug("[UserGroupDao : findUsersByGroupId] : group {}", groupId);
        try {
            String sql = "SELECT user_id as \"userId\", role FROM user_group WHERE group_id = :groupId";
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            return postgresClient.queryNative(sql, params, UserRoleData.class);
        } catch (Exception e) {
            log.error("[UserGroupDao : findUsersByGroupId] : error fetching members for group {}", groupId, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public UserRoleData findUserRole(UUID groupId, UUID userId) {
        log.debug("[UserGroupDao : findUserRole] : group {} user {}", groupId, userId);
        try {
            String sql = "SELECT user_id as \"userId\", role FROM user_group WHERE group_id = :groupId AND user_id = :userId";
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            params.put("userId", userId);
            List<UserRoleData> result = postgresClient.queryNative(sql, params, UserRoleData.class);
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            log.error("[UserGroupDao : findUserRole] : error fetching role for user {} in group {}", userId, groupId, e);
            throw SplitException.createException(ErrorCode.DATABASE_ERROR);
        }
    }
}
