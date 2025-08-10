package com.split.ai.split.service.model.request.userauth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String identifier;

    @NotBlank
    private String password;

    private Boolean rememberMe;
}
