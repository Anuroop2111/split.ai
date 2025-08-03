package com.split.ai.commons.postgres;

import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Map;

/**
 * Generic client interface for basic Postgres operations.
 */
public interface PostgresClient {

    <T> T insert(T object);

    <T> T partialUpdate(T object);

    <T> T partialUpdate(T object, LockModeType lockMode);

    <T> T partialUpdate(T object, LockModeType lockMode, int lockTimeout);

    <T> T upsert(T object);

    <T> T upsert(T object, LockModeType lockMode);

    <T> T upsert(T object, LockModeType lockMode, int lockTimeout);

    <T> T findById(Class<T> cls, Object id);

    <T> T findById(Class<T> cls, Object id, LockModeType lockMode);

    <T> T findById(Class<T> cls, Object id, LockModeType lockMode, int lockTimeout);

    <T> List<T> findAll(Class<T> cls, Map<String, Object> filters);

    <T> List<T> findAll(Class<T> cls, Map<String, Object> filters, LockModeType lockMode);

    <T> List<T> findAll(Class<T> cls, Map<String, Object> filters, LockModeType lockMode, int lockTimeout);

    <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls);

    <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls, LockModeType lockMode);

    <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls, LockModeType lockMode, int lockTimeout);

    <T> List<T> queryNative(String sql, Map<String, Object> params, Class<T> cls);

}
