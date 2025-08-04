package com.split.ai.split.service.core.mapper;

import org.mapstruct.Named;

import java.util.UUID;

public interface BaseServiceMapper {

    @Named("randomUUID")
    default UUID randomUUID(Object src) {
        return UUID.randomUUID();
    }

    @Named("currentEpochTime")
    default Long currentEpochTime(Object src) {
        return System.currentTimeMillis();
    }

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }
}
