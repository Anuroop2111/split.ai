package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.service.ISettlementService;
import com.split.ai.split.service.model.request.settlement.SettlementRequest;
import com.split.ai.split.service.model.response.settlement.SettlementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service handling settlement operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementService implements ISettlementService {
    @Override
    public void settleUp(SettlementRequest request) {
        // no-op
    }

    @Override
    public SettlementResponse getSettlement(String settlementId) {
        return new SettlementResponse();
    }
}
