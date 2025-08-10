package com.split.ai.split.service.server.controller;

import com.split.ai.split.service.core.mapper.UserAuthMapper;
import com.split.ai.split.service.core.service.IUserAuthService;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.model.request.userauth.LoginRequest;
import com.split.ai.split.service.model.request.userauth.LogoutRequest;
import com.split.ai.split.service.model.request.userauth.SignupRequest;
import com.split.ai.split.service.model.response.userauth.LoginResponse;
import com.split.ai.split.service.model.response.userauth.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/user-auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final IUserAuthService userAuthService;

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        log.info("[UserAuthController : signup] : userName={}", request.getUserName());
        SignupServiceResponse response = userAuthService.signup(UserAuthMapper.MAPPER.toServiceRequest(request));
        return UserAuthMapper.MAPPER.toSignupResponse(response);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        log.info("[UserAuthController : login] : identifier={}", request.getIdentifier());
        LoginServiceResponse response = userAuthService.login(UserAuthMapper.MAPPER.toServiceRequest(request));
        return UserAuthMapper.MAPPER.toLoginResponse(response);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
        log.info("[UserAuthController : logout] : noop");
        userAuthService.logout(UserAuthMapper.MAPPER.toServiceRequest(request));
    }
}
