package com.split.ai.split.service.core.service;

import com.split.ai.split.service.core.service.impl.IUserService;
import com.split.ai.split.service.model.request.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.UserGroupsResponse;
import com.split.ai.split.service.model.response.UserProfileResponse;
import com.split.ai.split.service.model.response.UserSuggestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service handling user business logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    @Override
    public UserProfileResponse getProfile(String userId) {
        return new UserProfileResponse();
    }

    @Override
    public void updateProfile(String userId, UpdateUserProfileRequest request) {
        // no-op
    }

    @Override
    public UserSuggestResponse suggestUsers(String query, Integer limit) {
        return new UserSuggestResponse();
    }

    @Override
    public UserGroupsResponse getGroups(String userId, Integer page, Integer size) {
        return new UserGroupsResponse();
    }
}
