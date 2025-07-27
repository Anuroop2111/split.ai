package com.split.ai.split.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Detailed group response along with active expenses.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupExpenseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1598743627045806325L;

    private UserGroupResponse userGroup;
    private List<ExpenseResponse> expenses;
}
