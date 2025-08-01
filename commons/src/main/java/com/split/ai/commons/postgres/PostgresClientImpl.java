package com.split.ai.commons.postgres;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JPA based implementation of {@link PostgresClient} backed by {@link EntityManager}.
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class PostgresClientImpl implements PostgresClient {

    @PersistenceContext
    private final EntityManager entityManager;

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
    public <T> T findById(Class<T> cls, Object id) {
        return entityManager.find(cls, id);
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
    public <T> List<T> query(String jpql, Map<String, Object> params, Class<T> cls) {
        TypedQuery<T> query = entityManager.createQuery(jpql, cls);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }
}
