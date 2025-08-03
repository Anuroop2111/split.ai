package com.split.ai.split.service.core.service.impl;

import com.split.ai.split.service.core.mapper.SettlementServiceMapper;
import com.split.ai.split.service.core.service.ISettlementService;
import com.split.ai.split.service.model.request.settlement.SettlementRequest;
import com.split.ai.split.service.model.response.settlement.SettlementResponse;
import com.split.ai.split.service.repository.dao.ISettlementDao;
import com.split.ai.split.service.repository.entity.SettlementEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service handling settlement operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementService implements ISettlementService {

    private final ISettlementDao settlementDao;

    @Override
    public void settleUp(SettlementRequest request) {
        log.info("[SettlementService : settleUp] : {}", request);
        SettlementEntity entity = SettlementServiceMapper.MAPPER.toEntity(request);
        settlementDao.save(entity);
    }

    @Override
    public SettlementResponse getSettlement(UUID settlementId) {
        log.info("[SettlementService : getSettlement] : {}", settlementId);
        SettlementEntity entity = settlementDao.findById(settlementId);
        return entity == null ? null : SettlementServiceMapper.MAPPER.toResponse(entity);
    }
}
