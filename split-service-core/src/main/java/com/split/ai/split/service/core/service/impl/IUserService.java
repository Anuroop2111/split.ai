package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.model.request.UpdateUserProfileRequest;
import com.split.ai.split.service.model.response.UserGroupsResponse;
import com.split.ai.split.service.model.response.UserProfileResponse;
import com.split.ai.split.service.model.response.UserSuggestResponse;

public interface IUserService {
    UserProfileResponse getProfile(String userId);

    void updateProfile(String userId, UpdateUserProfileRequest request);

    UserSuggestResponse suggestUsers(String query, Integer limit);

    UserGroupsResponse getGroups(String userId, Integer page, Integer size);
}
