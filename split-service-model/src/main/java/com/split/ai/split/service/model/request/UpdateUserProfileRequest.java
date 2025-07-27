package com.split.ai.split.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.LANGUAGE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Request to update user profile.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserProfileRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8475630961048441032L;

    @NotNull
    private UUID userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String emailId;

    private String phoneNumber;

    private LANGUAGE language;
}
