package com.split.ai.split.service.model.request.userauth;

import com.split.ai.split.service.model.enums.IDENTITY_PROVIDER;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String identifier;
    @NotBlank
    private String password;
    private Boolean rememberMe;

    @Builder.Default
    private IDENTITY_PROVIDER provider = IDENTITY_PROVIDER.LOCAL;
}
