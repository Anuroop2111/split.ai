package com.split.ai.split.service.model.request.userauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String emailId;

    @NotBlank
    private String password;
}
