package com.split.ai.split.service.model.response.settlement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.split.ai.split.service.model.enums.CURRENCY;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response representing a settlement entry.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2324768235789345345L;

    private UUID settlementId;
    private UUID groupId;
    private UUID fromUserId;
    private UUID toUserId;
    private BigDecimal amount;
    private CURRENCY currencyType;
    private String note;
    private Long createdAt;
}
