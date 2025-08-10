package com.split.ai.split.service.core.userauth;

import com.split.ai.split.service.core.userauth.model.LoginServiceRequest;
import com.split.ai.split.service.core.userauth.model.LoginServiceResponse;
import com.split.ai.split.service.core.userauth.model.LogoutServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceRequest;
import com.split.ai.split.service.core.userauth.model.SignupServiceResponse;
import com.split.ai.split.service.repository.IdentityDao;
import com.split.ai.split.service.repository.LocalCredentialsDao;
import com.split.ai.split.service.repository.entity.IdentityEntity;
import com.split.ai.split.service.repository.entity.LocalCredentialsEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final IdentityDao identityDao;
    private final LocalCredentialsDao localCredentialsDao;
    private final PasswordService passwordService;

    public SignupServiceResponse signup(SignupServiceRequest request) {
        log.info("[UserAuthService : signup] : userName={}", request.getUserName());
        IdentityEntity identityEntity = IdentityEntity.builder()
            .userName(request.getUserName())
            .emailId(request.getEmailId())
            .build();
        identityEntity = identityDao.save(identityEntity);

        String encodedPassword = passwordService.encode(request.getPassword());
        LocalCredentialsEntity credentialsEntity = LocalCredentialsEntity.builder()
            .identity(identityEntity)
            .passwordHash(encodedPassword)
            .build();
        localCredentialsDao.save(credentialsEntity);

        return SignupServiceResponse.builder()
            .userId(identityEntity.getId())
            .userName(identityEntity.getUserName())
            .build();
    }

    public LoginServiceResponse login(LoginServiceRequest request) {
        log.info("[UserAuthService : login] : identifier={}", request.getIdentifier());
        Optional<IdentityEntity> identityOpt = identityDao.findByUserNameOrEmailId(request.getIdentifier(), request.getIdentifier());
        IdentityEntity identityEntity = identityOpt.orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        LocalCredentialsEntity credentialsEntity = localCredentialsDao.findByIdentityId(identityEntity.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        boolean matches = passwordService.matchesAndUpgrade(request.getPassword(), credentialsEntity.getPasswordHash(), newHash -> {
            credentialsEntity.setPasswordHash(newHash);
            localCredentialsDao.save(credentialsEntity);
        });
        if (!matches) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return LoginServiceResponse.builder()
            .userId(identityEntity.getId())
            .userName(identityEntity.getUserName())
            .build();
    }

    public void logout(LogoutServiceRequest request) {
        log.info("[UserAuthService : logout] : noop");
    }
}
