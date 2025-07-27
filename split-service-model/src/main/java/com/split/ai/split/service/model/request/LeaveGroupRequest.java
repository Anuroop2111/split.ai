package com.split.ai.split.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Request for a member to leave a group.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveGroupRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6486765462893549643L;

    @NotNull
    private UUID groupId;

    @NotNull
    private UUID userId;
}
