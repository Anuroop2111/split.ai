package com.split.ai.split.service.model.request.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.GROUP_TYPE;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Request to update a group's details.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateGroupRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 5713921175270602855L;

    @NotNull
    private UUID groupId;

    private String newGroupName;
    private GROUP_TYPE newGroupType;
}
