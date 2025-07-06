package com.split.ai.commons.postgres;

import java.util.List;
import java.util.Map;

/**
 * Generic client interface for basic Postgres operations.
 */
public interface PostgresClient {

    <T> T insert(T object);

    <T> T update(T object);

    <T> T upsert(T object);

    <T> T findById(Class<T> clazz, Object id);

    <T> List<T> findAll(Class<T> clazz, Map<String, Object> filters);

    <T> List<T> query(String jpql, Map<String, Object> params, Class<T> clazz);
}
