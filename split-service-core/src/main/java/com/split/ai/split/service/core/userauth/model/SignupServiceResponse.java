package com.split.ai.split.service.core.userauth.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupServiceResponse {

    private UUID userId;
    private String userName;
}
