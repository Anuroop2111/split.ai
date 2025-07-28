package com.split.ai.split.service.core.service;

import com.split.ai.split.service.model.request.settlement.SettlementRequest;
import com.split.ai.split.service.model.response.settlement.SettlementResponse;

public interface ISettlementService {
    void settleUp(SettlementRequest request);

    SettlementResponse getSettlement(String settlementId);
}
