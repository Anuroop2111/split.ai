package com.split.ai.split.service.model.response.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.*;
import com.split.ai.split.service.model.response.user.UserExpenseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Response containing expense details.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3317623450938642376L;

    private UUID expenseId;
    private UUID groupId;
    private UUID payerId;
    private BigDecimal amount;
    private Long expenseDate;
    private String description;
    private SplitMode splitMode;
    private CurrencyType currency;
    private Category category;
    private SubCategory subCategory;
    private ExpenseStatus expenseStatus;
    private Long createdAt;
    private List<UserExpenseDto> userExpenseDetails;
}
