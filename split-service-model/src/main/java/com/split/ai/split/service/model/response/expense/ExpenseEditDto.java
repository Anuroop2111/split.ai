package com.split.ai.split.service.model.response.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * DTO capturing one edit history entry of an expense.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseEditDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -7338268945012745668L;

    private UUID editedBy;
    private Long editedAt;

    private Map<String, ChangeDto> changes;
    private Map<String, ChangeDto> userShareChanges;
}
