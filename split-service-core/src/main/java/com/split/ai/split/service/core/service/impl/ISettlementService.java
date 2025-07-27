package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.model.request.SettlementRequest;
import com.split.ai.split.service.model.response.SettlementResponse;

public interface ISettlementService {
    void settleUp(SettlementRequest request);

    SettlementResponse getSettlement(String settlementId);
}
