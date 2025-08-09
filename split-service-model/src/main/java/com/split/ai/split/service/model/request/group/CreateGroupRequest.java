package com.split.ai.split.service.model.request.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.CURRENCY;
import com.split.ai.split.service.model.enums.GROUP_TYPE;
import com.split.ai.split.service.model.request.user.UserRoleData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Request to create a group.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGroupRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5430597607738643671L;

    @NotBlank
    private String groupName;

    @NotNull
    private UUID userInitiated; // Will be ADMIN by default

    private List<UserRoleData> additionalUserData;

    private GROUP_TYPE GROUPTYPE;

    private CURRENCY baseCurrency;
}
