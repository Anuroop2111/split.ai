package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.request.userauth.SignupRequest;
import com.split.ai.split.service.model.response.userauth.LoginResponse;
import com.split.ai.split.service.model.response.userauth.SignupResponse;
import com.split.ai.split.service.repository.entity.IdentityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface UserAuthServiceMapper extends BaseServiceMapper {

    UserAuthServiceMapper MAPPER = Mappers.getMapper(UserAuthServiceMapper.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userName", source = "request.userName")
    SignupResponse toSignupResponse(SignupRequest request, UUID userId);

    @Mapping(target = "userName", source = "identifier")
    LoginResponse toLoginResponse(IdentityEntity identityEntity);
}
