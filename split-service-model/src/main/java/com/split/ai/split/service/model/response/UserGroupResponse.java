package com.split.ai.split.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.GroupType;
import com.split.ai.split.service.model.enums.SettleMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Response describing a group the user is part of.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroupResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -5096029048812009946L;

    private UUID groupId;
    private String groupName;
    private GroupType groupType;
    private SettleMode settleMode;
}
