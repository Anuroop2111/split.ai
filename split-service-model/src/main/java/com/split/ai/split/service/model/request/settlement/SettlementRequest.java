package com.split.ai.split.service.model.request.settlement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request to settle up between users.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6715643255431458720L;

    private UUID groupId;

    @NotNull
    private UUID payerId;

    @NotNull
    private UUID receiverId;

    @NotNull
    private BigDecimal amount;

    private CurrencyType currency;
}
