package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.response.user.UserProfileResponse;
import com.split.ai.split.service.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserServiceMapper extends BaseServiceMapper {

    UserServiceMapper MAPPER = Mappers.getMapper(UserServiceMapper.class);

    UserProfileResponse convert(UserEntity entity);
}
