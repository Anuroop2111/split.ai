package com.split.ai.split.service.repository.dao.impl;

import com.split.ai.split.service.repository.dao.ISettlementDao;
import com.split.ai.split.service.repository.entity.SettlementEntity;

import java.util.UUID;

public class SettlementDao implements ISettlementDao {

    @Override
    public void save(SettlementEntity entity) {

    }

    @Override
    public void update(SettlementEntity entity) {

    }

    @Override
    public SettlementEntity findById(UUID settlementId) {
        return SettlementEntity.builder().build();
    }
}
