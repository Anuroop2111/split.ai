package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.service.IPasswordService;
import com.split.ai.split.service.core.service.IUserAuthService;
import com.split.ai.split.service.core.userauth.model.LoginServiceRequest;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.LogoutServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.model.enums.IDENTITY_PROVIDER;
import com.split.ai.split.service.repository.IdentityDao;
import com.split.ai.split.service.repository.LocalCredentialsDao;
import com.split.ai.split.service.repository.entity.IdentityEntity;
import com.split.ai.split.service.repository.entity.LocalCredentialsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService implements IUserAuthService {

    private final IdentityDao identityDao;
    private final LocalCredentialsDao localCredentialsDao;
    private final IPasswordService passwordService;

    public SignupServiceResponse signup(SignupServiceRequest request) {
        log.info("[UserAuthService : signup] : userName={}", request.getUserName());
        UUID userId = UUID.randomUUID();
        IdentityEntity identityEntity = IdentityEntity.builder()
                .identityId(UUID.randomUUID())
                .userId(userId)
                .provider(IDENTITY_PROVIDER.LOCAL)
                .identifier(request.getEmailId())
                .verified(Boolean.FALSE)
                .build();
        identityDao.save(identityEntity);

        String encodedPassword = passwordService.encode(request.getPassword());
        LocalCredentialsEntity credentialsEntity = LocalCredentialsEntity.builder()
                .userId(userId)
                .passwordHash(encodedPassword)
                .hashAlgo("argon2id")
                .build();
        localCredentialsDao.save(credentialsEntity);

        return SignupServiceResponse.builder()
                .userId(userId)
                .userName(request.getUserName())
                .build();
    }

    public LoginServiceResponse login(LoginServiceRequest request) {
        log.info("[UserAuthService : login] : identifier={}", request.getIdentifier());
        Optional<IdentityEntity> identityOpt = identityDao.findByProviderAndIdentifier(IDENTITY_PROVIDER.LOCAL, request.getIdentifier());
        IdentityEntity identityEntity = identityOpt.orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        LocalCredentialsEntity credentialsEntity = localCredentialsDao.findByUserId(identityEntity.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        boolean matches = passwordService.matchesAndUpgrade(request.getPassword(), credentialsEntity.getPasswordHash(), newHash -> {
            credentialsEntity.setPasswordHash(newHash);
            localCredentialsDao.update(credentialsEntity);
        });
        if (!matches) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return LoginServiceResponse.builder()
                .userId(identityEntity.getUserId())
                .userName(identityEntity.getIdentifier())
                .build();
    }

    // todo
    public void logout(LogoutServiceRequest request) {
        log.info("[UserAuthService : logout] : noop");
    }
}
