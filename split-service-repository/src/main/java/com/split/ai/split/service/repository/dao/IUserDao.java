package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.UserEntity;

import java.util.UUID;

public interface IUserDao {

    void save(UserEntity entity);

    void update(UserEntity entity);

    UserEntity findById(UUID userId);

}
