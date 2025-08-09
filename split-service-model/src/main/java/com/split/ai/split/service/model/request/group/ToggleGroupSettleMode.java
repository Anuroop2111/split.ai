package com.split.ai.split.service.model.request.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.SETTLE_MODE;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Request to toggle a group's settle mode.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToggleGroupSettleMode implements Serializable {
    @Serial
    private static final long serialVersionUID = -4874568392430909921L;

    @NotNull
    private UUID groupId;

    private SETTLE_MODE settleMode;
}
