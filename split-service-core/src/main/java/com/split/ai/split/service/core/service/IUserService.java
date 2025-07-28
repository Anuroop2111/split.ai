package com.split.ai.split.service.core.service;

import com.split.ai.split.service.model.request.user.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.user.UserGroupsResponse;
import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.model.response.user.UserSuggestResponse;

public interface IUserService {
    UserProfileResponse getProfile(String userId);

    void updateProfile(String userId, UpdateUserProfileRequest request);

    UserSuggestResponse suggestUsers(String query, Integer limit);

    UserGroupsResponse getGroups(String userId, Integer page, Integer size);
}
