package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.model.response.user.UserGroupResponse;
import com.split.ai.split.service.model.response.user.UserSuggestDto;
import com.split.ai.split.service.repository.entity.UserEntity;
import com.split.ai.split.service.model.request.user.UpdateUserProfileRequest;
import com.split.ai.split.service.repository.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserServiceMapper extends BaseServiceMapper {

    UserServiceMapper MAPPER = Mappers.getMapper(UserServiceMapper.class);

    UserProfileResponse convert(UserEntity entity);

    UserSuggestDto convertToSuggest(UserEntity entity);

    UserEntity convert(UpdateUserProfileRequest request);

    UserGroupResponse convert(GroupEntity entity);
}
