package com.split.ai.split.service.model.request.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.*;
import com.split.ai.split.service.model.request.split.SplitRequest;
import com.split.ai.split.service.model.response.user.UserExpenseDto;
import jakarta.validation.constraints.NotNull;
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
 * Request to create an expense.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateExpenseRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -7926909423475367034L;

    private UUID groupId;

    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private UUID payerId;

    private SplitMode splitMode;

    private CurrencyType currency;

    private SubCategory subCategory;

    private Category category;

    private List<UserExpenseDto> userExpenseDetails;

    private SplitRequest splitRequest;
}
