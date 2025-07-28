package com.split.ai.split.service.model.request.expense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.*;
import com.split.ai.split.service.model.request.split.SplitRequest;
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
 * Request to update an expense.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateExpenseRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8353749821743269398L;

    @NotNull
    private UUID expenseId;

    @NotNull
    private UUID editedBy;

    private UUID payer;
    private BigDecimal amount;
    private Long expenseDate;
    private SplitMode splitMode;
    private CurrencyType currency;
    private Category category;
    private SubCategory subCategory;
    private String description;
    private String metaData;
    private List<UserExpenseData> userExpenseDetails;
    private SplitRequest splitRequest;
}
