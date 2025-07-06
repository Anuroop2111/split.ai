package com.split.ai.commons.postgres;

import java.util.List;
import java.util.Map;

/**
 * Generic client interface for basic Postgres operations.
 */
public interface PostgresClient {

    <T> T insert(String tableName, T object);

    <T> int update(String tableName, T object, Map<String, Object> filters);

    <T> int upsert(String tableName, T object, Map<String, Object> filters);

    <T> T findById(String tableName, Object id, Class<T> clazz);

    <T> T find(String tableName, Map<String, Object> filters, Class<T> clazz);

    <T> List<T> findAll(String tableName, Map<String, Object> filters, Class<T> clazz);

    <T> List<T> query(String sql, Map<String, Object> params, Class<T> clazz);
}
