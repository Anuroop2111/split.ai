package com.split.ai.split.service.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseServiceMapper extends BaseServiceMapper {

    ExpenseServiceMapper MAPPER = Mappers.getMapper(ExpenseServiceMapper.class);
}
