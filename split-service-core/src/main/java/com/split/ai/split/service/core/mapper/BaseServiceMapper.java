package com.split.ai.split.service.core.mapper;

import com.split.ai.split.service.repository.entity.GroupEntity;
import com.split.ai.split.service.repository.entity.UserEntity;
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

    @Named("getUser")
    default UserEntity getUser(UUID userId) {
        return UserEntity.builder()
                .userId(userId)
                .build();
    }

    @Named("getGroup")
    default GroupEntity getGroup(UUID groupId) {
        return GroupEntity.builder()
                .groupId(groupId)
                .build();
    }
}
