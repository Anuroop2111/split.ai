package com.split.ai.split.service.repository.dao;

import com.split.ai.split.service.repository.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

public interface IGroupDao {

    void save(GroupEntity entity);

    void update(GroupEntity entity);

    GroupEntity findById(UUID groupId);

    List<GroupEntity> findByUserIdPaginated(UUID userId, Integer page, Integer size);

}
