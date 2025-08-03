package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.SettlementEntity;

import java.util.UUID;

public interface ISettlementDao {

    void save(SettlementEntity entity);

    void update(SettlementEntity entity);

    SettlementEntity findById(UUID settlementId);
}
