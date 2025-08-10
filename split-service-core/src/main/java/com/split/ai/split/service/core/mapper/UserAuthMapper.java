package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.core.userauth.model.LoginServiceRequest;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.LogoutServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.model.request.userauth.LoginRequest;
import com.split.ai.split.service.model.request.userauth.LogoutRequest;
import com.split.ai.split.service.model.request.userauth.SignupRequest;
import com.split.ai.split.service.model.response.userauth.LoginResponse;
import com.split.ai.split.service.model.response.userauth.SignupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAuthMapper extends BaseServiceMapper {

    UserAuthMapper MAPPER = Mappers.getMapper(UserAuthMapper.class);

    SignupServiceRequest toServiceRequest(SignupRequest request);

    SignupResponse toSignupResponse(SignupServiceResponse response);

    LoginServiceRequest toServiceRequest(LoginRequest request);

    LoginResponse toLoginResponse(LoginServiceResponse response);

    LogoutServiceRequest toServiceRequest(LogoutRequest request);
}
