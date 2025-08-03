package com.split.ai.commons.postgres;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
public class PostgresClientImpl implements PostgresClient {

    private static final Map<String, Object> DEFAULT_LOCK_TIME = Map.of("javax.persistence.lock.timeout", 5);

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public <T> T insert(T object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public <T> T partialUpdate(T object) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(cls, id);
        if (existing == null) throw new EntityNotFoundException(cls.getSimpleName() + " with ID " + id + " not found");
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T partialUpdate(T object, LockModeType lockMode) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(cls, id, lockMode, DEFAULT_LOCK_TIME);
        if (existing == null) {
            throw new EntityNotFoundException(cls.getSimpleName() + " with ID " + id + " not found");
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T partialUpdate(T object, LockModeType lockMode, int lockTimeout) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(
                cls,
                id,
                lockMode,
                Map.of("javax.persistence.lock.timeout", lockTimeout)
        );
        if (existing == null) {
            throw new EntityNotFoundException(cls.getSimpleName() + " with ID " + id + " not found");
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T upsert(T object) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(cls, id);
        if (existing == null) {
            return entityManager.merge(object);
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T upsert(T object, LockModeType lockMode) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(cls, id, lockMode, DEFAULT_LOCK_TIME);
        if (existing == null) {
            return entityManager.merge(object);
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T upsert(T object, LockModeType lockMode, int lockTimeout) {
        Class<T> cls = (Class<T>) object.getClass();
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        T existing = entityManager.find(
                cls,
                id,
                lockMode,
                Map.of("javax.persistence.lock.timeout", lockTimeout)
        );
        if (existing == null) {
            return entityManager.merge(object);
        }
        BeanUtils.copyProperties(object, existing, getNullPropertyNames(object));
        return existing;
    }

    @Override
    public <T> T findById(Class<T> cls, Object id) {
        return entityManager.find(cls, id);
    }

    @Override
    public <T> T findById(Class<T> cls, Object id, LockModeType lockMode) {
        return entityManager.find(cls, id, lockMode, DEFAULT_LOCK_TIME);
    }

    @Override
    public <T> T findById(Class<T> cls, Object id, LockModeType lockMode, int lockTimeout) {
        return entityManager.find(
                cls,
                id,
                lockMode,
                Map.of("javax.persistence.lock.timeout", lockTimeout)
        );
    }

    @Override
    public <T> List<T> findAll(Class<T> cls, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cls);
        Root<T> root = cq.from(cls);
        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach((k, v) -> predicates.add(cb.equal(root.get(k), v)));
            cq.where(predicates.toArray(new Predicate[0]));
        }
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public <T> List<T> findAll(Class<T> cls, Map<String, Object> filters, LockModeType lockMode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cls);
        Root<T> root = cq.from(cls);
        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach((k, v) -> predicates.add(cb.equal(root.get(k), v)));
            cq.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<T> query = entityManager.createQuery(cq);
        query.setLockMode(lockMode);
        return query.getResultList();
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
        query.setLockMode(lockMode);
        query.setHint("javax.persistence.lock.timeout", lockTimeout);
        return query.getResultList();
    }

    @Override
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls) {
        TypedQuery<T> query = entityManager.createQuery(jpql, cls);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
