package com.split.ai.split.service.core.userauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginServiceRequest {

    private String identifier;
    private String password;
    private Boolean rememberMe;
}
