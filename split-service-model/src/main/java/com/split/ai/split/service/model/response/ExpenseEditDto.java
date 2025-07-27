package com.split.ai.split.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private UUID payerOld;
    private UUID payerNew;
    private BigDecimal amountOld;
    private BigDecimal amountNew;
    private Long expenseDateOld;
    private Long expenseDateNew;
    private SplitMode splitModeOld;
    private SplitMode splitModeNew;
    private CurrencyType currencyOld;
    private CurrencyType currencyNew;
    private Category categoryOld;
    private Category categoryNew;
    private SubCategory subCategoryOld;
    private SubCategory subCategoryNew;
    private String descriptionOld;
    private String descriptionNew;
    private String metaDataOld;
    private String metaDataNew;
    private Map<UUID, BigDecimal> userSharesOld;
    private Map<UUID, BigDecimal> userSharesNew;
}
