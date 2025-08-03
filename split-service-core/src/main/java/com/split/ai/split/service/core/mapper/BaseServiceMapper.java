package com.split.ai.split.service.core.mapper;

import org.mapstruct.Named;

import java.util.UUID;

public interface BaseServiceMapper {

    @Named("randomUUID")
    default UUID randomUUID() {
        return UUID.randomUUID();
    }
}
