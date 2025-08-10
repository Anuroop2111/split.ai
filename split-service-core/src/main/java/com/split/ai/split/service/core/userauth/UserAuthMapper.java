package com.split.ai.split.service.core.userauth;

import com.split.ai.split.service.core.userauth.model.LoginServiceRequest;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.LogoutServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.model.userauth.LoginRequest;
import com.split.ai.split.service.model.userauth.LoginResponse;
import com.split.ai.split.service.model.userauth.LogoutRequest;
import com.split.ai.split.service.model.userauth.SignupRequest;
import com.split.ai.split.service.model.userauth.SignupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    SignupServiceRequest toServiceRequest(SignupRequest request);

    SignupResponse toSignupResponse(SignupServiceResponse response);

    LoginServiceRequest toServiceRequest(LoginRequest request);

    LoginResponse toLoginResponse(LoginServiceResponse response);

    LogoutServiceRequest toServiceRequest(LogoutRequest request);
}
