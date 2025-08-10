package com.split.ai.split.service.core.service;

import com.split.ai.split.service.core.userauth.model.LoginServiceRequest;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.LogoutServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;

public interface IUserAuthService {

    SignupServiceResponse signup(SignupServiceRequest request);

    LoginServiceResponse login(LoginServiceRequest request);

    void logout(LogoutServiceRequest request);
}
