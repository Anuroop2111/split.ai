package com.split.ai.split.service.core.userauth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Value("${security.password.pepper}")
    private String pepper;

    public String encode(String rawPassword) {
        String preHash = hmacSha256(rawPassword);
        return passwordEncoder.encode(preHash);
    }

    public boolean matchesAndUpgrade(String rawPassword, String encodedPassword, java.util.function.Consumer<String> upgradeAction) {
        String preHash = hmacSha256(rawPassword);
        boolean matches = passwordEncoder.matches(preHash, encodedPassword);
        if (matches && passwordEncoder.upgradeEncoding(encodedPassword)) {
            String newEncoded = passwordEncoder.encode(preHash);
            upgradeAction.accept(newEncoded);
        }
        return matches;
    }

    private String hmacSha256(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(pepper.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] result = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            log.error("[PasswordService : hmacSha256] : error while hashing", e);
            throw new IllegalStateException("Could not hash password", e);
        }
    }
}
