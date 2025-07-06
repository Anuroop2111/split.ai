package com.split.ai.commons.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JPA based implementation of {@link PostgresClient} backed by {@link EntityManager}.
 */
@Repository
@Transactional
public class PostgresClientImpl implements PostgresClient {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> T insert(T object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public <T> T update(T object) {
        return entityManager.merge(object);
    }

    @Override
    public <T> T upsert(T object) {
        return entityManager.merge(object);
    }

    @Override
    public <T> T findById(Class<T> clazz, Object id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        if (filters != null && !filters.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach((k, v) -> predicates.add(cb.equal(root.get(k), v)));
            cq.where(predicates.toArray(new Predicate[0]));
        }
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> clazz) {
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }
}
