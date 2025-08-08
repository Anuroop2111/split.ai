package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.model.request.settlement.SettlementRequest;
import com.split.ai.split.service.model.response.settlement.SettlementResponse;
import com.split.ai.split.service.repository.entity.SettlementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementServiceMapper extends BaseServiceMapper {

    SettlementServiceMapper MAPPER = Mappers.getMapper(SettlementServiceMapper.class);

    @Mapping(target = "settlementId", source = "request", qualifiedByName = "randomUUID")
    @Mapping(target = "group", source = "groupId", qualifiedByName = "getGroup")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "fromUserId", source = "payerId", qualifiedByName = "getUser")
    @Mapping(target = "fromUserId", source = "payerId")
    @Mapping(target = "toUserId", source = "receiverId", qualifiedByName = "getUser")
    @Mapping(target = "toUserId", source = "receiverId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currencyType", source = "currency")
    @Mapping(target = "createdAt", source = "request", qualifiedByName = "currentEpochTime")
    SettlementEntity toEntity(SettlementRequest request);

    @Mapping(target = "settlementId", source = "settlementId")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "fromUserId", source = "fromUserId")
    @Mapping(target = "toUserId", source = "toUserId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currencyType", source = "currencyType")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "createdAt", source = "createdAt")
    SettlementResponse toResponse(SettlementEntity entity);
}
