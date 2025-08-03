package com.split.ai.split.service.model.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.LANGUAGE;
import com.split.ai.split.service.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Response containing user profile details.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1046631501063901127L;

    private String userId;
    private String emailId;
    private String fullName;
    private String userName;
    private UserStatus userStatus;
    private LANGUAGE language;
}
