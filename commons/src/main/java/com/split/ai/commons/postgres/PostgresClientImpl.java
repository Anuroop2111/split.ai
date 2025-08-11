package com.split.ai.commons.postgres;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * JPA based implementation of {@link PostgresClient} backed by {@link EntityManager}.
 */
@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostgresClientImpl implements PostgresClient {

    private static final int DEFAULT_LOCK_TIME = 5;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public <T> T insert(T object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public <T> T partialUpdate(T object) {
        return doPartialUpdate(object, null, null);
    }

    @Override
    public <T> T partialUpdate(T object, LockModeType lockMode) {
        return doPartialUpdate(object, lockMode, null);
    }

    @Override
    public <T> T partialUpdate(T object, LockModeType lockMode, int lockTimeout) {
        return doPartialUpdate(object, lockMode, lockTimeout);
    }

    @Override
    public <T> T upsert(T object) {
        return doUpsert(object, null, null);
    }

    @Override
    public <T> T upsert(T object, LockModeType lockMode) {
        return doUpsert(object, lockMode, null);
    }

    @Override
    public <T> T upsert(T object, LockModeType lockMode, int lockTimeout) {
        return doUpsert(object, lockMode, lockTimeout);
    }

    @Override
    public <T> T findById(Class<T> cls, Object id) {
        return findEntity(cls, id, null, null);
    }

    @Override
    public <T> T findById(Class<T> cls, Object id, LockModeType lockMode) {
        return findEntity(cls, id, lockMode, null);
    }

    @Override
    public <T> T findById(Class<T> cls, Object id, LockModeType lockMode, int lockTimeout) {
        return findEntity(cls, id, lockMode, lockTimeout);
    }

    @Override
    public <T> List<T> findAll(Class<T> cls, Map<String, Object> filters) {
        return findAll(cls, filters, null, 0);
    }

    @Override
    public <T> List<T> findAll(Class<T> cls, Map<String, Object> filters, LockModeType lockMode) {
        return findAll(cls, filters, lockMode, DEFAULT_LOCK_TIME);
    }

    @Override
    public <T> List<T> findAll(Class<T> cls, Map<String, Object> filters, LockModeType lockMode, int lockTimeout) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cls);
        Root<T> root = cq.from(cls);
        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach((k, v) -> predicates.add(cb.equal(root.get(k), v)));
            cq.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<T> query = entityManager.createQuery(cq);
        applyLockOptions(query, lockMode, lockTimeout, false);
        return query.getResultList();
    }

    @Override
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls) {
        return query(jpql, params, cls, null, 0);
    }

    @Override
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls, LockModeType lockMode) {
        return query(jpql, params, cls, lockMode, DEFAULT_LOCK_TIME);
    }

    @Override
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls, LockModeType lockMode, int lockTimeout) {
        TypedQuery<T> query = entityManager.createQuery(jpql, cls);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        applyLockOptions(query, lockMode, lockTimeout, true);
        return query.getResultList();
    }

    @Override
    public <T> List<T> queryNative(String sql, Map<String, Object> params, Class<T> cls) {
        jakarta.persistence.Query query = entityManager.createNativeQuery(sql, cls);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }

    private <T> T doPartialUpdate(T object, LockModeType lockMode, Integer lockTimeout) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = findEntity(cls, id, lockMode, lockTimeout);
        if (existing == null) {
            log.error("[PostgresClientImpl : doPartialUpdate] : entity {} with id {} not found", cls.getSimpleName(), id);
            throw SplitException.createException(ErrorCode.ENTITY_NOT_FOUND);
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    private <T> T doUpsert(T object, LockModeType lockMode, Integer lockTimeout) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = findEntity(cls, id, lockMode, lockTimeout);
        if (existing == null) {
            return entityManager.merge(object);
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    private <T> T findEntity(Class<T> cls, Object id, LockModeType lockMode, Integer lockTimeout) {
        if (lockMode == null) {
            return entityManager.find(cls, id);
        }
        Map<String, Object> hints = Map.of(
                "javax.persistence.lock.timeout",
                (lockTimeout != null && lockTimeout > 0) ? lockTimeout : DEFAULT_LOCK_TIME
        );
        return entityManager.find(cls, id, lockMode, hints);
    }

    private void applyLockOptions(TypedQuery<?> query, LockModeType lockMode, Integer lockTimeout, boolean extendScope) {
        if (lockMode != null) {
            query.setLockMode(lockMode);
            if (extendScope) {
                query.setHint("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);
            }
            query.setHint(
                    "javax.persistence.lock.timeout",
                    (lockTimeout != null && lockTimeout > 0) ? lockTimeout : DEFAULT_LOCK_TIME
            );
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
