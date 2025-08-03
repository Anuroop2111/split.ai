package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.UserServiceMapper;
import com.split.ai.split.service.core.service.IUserService;
import com.split.ai.split.service.core.utils.ValidationUtil;
import com.split.ai.split.service.model.request.user.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.user.UserGroupsResponse;
import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.model.response.user.UserSuggestResponse;
import com.split.ai.split.service.repository.dao.IGroupDao;
import com.split.ai.split.service.repository.dao.IUserDao;
import com.split.ai.split.service.repository.entity.GroupEntity;
import com.split.ai.split.service.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service handling user business logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IGroupDao groupDao;

    @Override
    public UserProfileResponse getProfile(UUID userId) {
        UserEntity user = userDao.findById(userId);
        return UserServiceMapper.MAPPER.convert(user);
    }

    @Override
    public void updateProfile(UUID userId, UpdateUserProfileRequest request) {
        log.info("[UserService : updateProfile] : updating {}", userId);
        UserEntity entity = UserServiceMapper.MAPPER.convert(request);
        entity.setUserId(userId);
        entity.setUpdatedAt(System.currentTimeMillis());
        userDao.update(entity);
    }

    @Override
    public UserSuggestResponse suggestUsers(String query, Integer limit) {
        ValidationUtil.validateSuggestUserQuery(query);
        List<UserEntity> users = userDao.suggestUsers(query, limit);
        return UserSuggestResponse.builder()
                .userSuggestDtos(users.stream()
                        .map(UserServiceMapper.MAPPER::convertToSuggest)
                        .toList())
                .build();
    }

    @Override
    public UserGroupsResponse getGroups(UUID userId, Integer page, Integer size) {
        List<GroupEntity> groups = groupDao.findByUserIdPaginated(userId, page, size);
        return UserGroupsResponse.builder()
                .userGroups(groups.stream()
                        .map(UserServiceMapper.MAPPER::convert)
                        .toList())
                .build();
    }
}
