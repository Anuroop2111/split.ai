package com.split.ai.split.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementServiceMapper extends BaseServiceMapper {

    SettlementServiceMapper MAPPER = Mappers.getMapper(SettlementServiceMapper.class);
}
