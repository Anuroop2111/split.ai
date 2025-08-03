package com.split.ai.split.service.core.service;

import com.split.ai.split.service.model.request.user.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.user.UserGroupsResponse;
import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.model.response.user.UserSuggestResponse;

import java.util.UUID;

public interface IUserService {

    UserProfileResponse getProfile(UUID userId);

    void updateProfile(UUID userId, UpdateUserProfileRequest request);

    UserSuggestResponse suggestUsers(String query, Integer limit);

    UserGroupsResponse getGroups(UUID userId, Integer page, Integer size);
}
