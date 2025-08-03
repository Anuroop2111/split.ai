package com.split.ai.split.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupServiceMapper extends BaseServiceMapper {

    GroupServiceMapper MAPPER = Mappers.getMapper(GroupServiceMapper.class);
}
