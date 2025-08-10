package com.split.ai.split.service.server.userauth;

import com.split.ai.split.service.core.userauth.UserAuthMapper;
import com.split.ai.split.service.core.userauth.UserAuthService;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.model.userauth.LoginRequest;
import com.split.ai.split.service.model.userauth.LoginResponse;
import com.split.ai.split.service.model.userauth.LogoutRequest;
import com.split.ai.split.service.model.userauth.SignupRequest;
import com.split.ai.split.service.model.userauth.SignupResponse;
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

    private final UserAuthService userAuthService;
    private final UserAuthMapper userAuthMapper;

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        log.info("[UserAuthController : signup] : userName={}", request.getUserName());
        SignupServiceResponse response = userAuthService.signup(userAuthMapper.toServiceRequest(request));
        return userAuthMapper.toSignupResponse(response);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        log.info("[UserAuthController : login] : identifier={}", request.getIdentifier());
        LoginServiceResponse response = userAuthService.login(userAuthMapper.toServiceRequest(request));
        return userAuthMapper.toLoginResponse(response);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
        log.info("[UserAuthController : logout] : noop");
        userAuthService.logout(userAuthMapper.toServiceRequest(request));
    }
}
