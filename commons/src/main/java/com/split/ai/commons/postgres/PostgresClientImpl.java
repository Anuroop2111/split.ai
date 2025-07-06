package com.split.ai.commons.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of {@link PostgresClient} using {@link NamedParameterJdbcTemplate}.
 */
@Component
public class PostgresClientImpl implements PostgresClient {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public PostgresClientImpl(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public <T> T insert(String tableName, T object) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
                .withTableName(tableName)
                .usingGeneratedKeyColumns("id");
        Number id = insert.executeAndReturnKey(new BeanPropertySqlParameterSource(object));
        // attempt to set generated id back on object if property exists
        try {
            object.getClass().getMethod("setId", Long.class).invoke(object, id.longValue());
        } catch (Exception ignored) {
        }
        return object;
    }

    @Override
    public <T> int update(String tableName, T object, Map<String, Object> filters) {
        Map<String, Object> values = BeanUtils.toMap(object);
        String sql = "UPDATE " + tableName + " SET " + QueryUtils.updateAssignments(values)
                + QueryUtils.whereClause(filters);
        MapSqlParameterSource source = new MapSqlParameterSource();
        values.forEach(source::addValue);
        if (filters != null) {
            filters.forEach(source::addValue);
        }
        return jdbcTemplate.update(sql, source);
    }

    @Override
    public <T> int upsert(String tableName, T object, Map<String, Object> filters) {
        // naive implementation: try update first, if nothing updated then insert
        int updated = update(tableName, object, filters);
        if (updated == 0) {
            insert(tableName, object);
            return 1;
        }
        return updated;
    }

    @Override
    public <T> T findById(String tableName, Object id, Class<T> clazz) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = :id";
        MapSqlParameterSource source = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, source, BeanPropertyRowMapper.newInstance(clazz));
    }

    @Override
    public <T> T find(String tableName, Map<String, Object> filters, Class<T> clazz) {
        String sql = "SELECT * FROM " + tableName + QueryUtils.whereClause(filters) + " LIMIT 1";
        return jdbcTemplate.queryForObject(sql, filters, BeanPropertyRowMapper.newInstance(clazz));
    }

    @Override
    public <T> List<T> findAll(String tableName, Map<String, Object> filters, Class<T> clazz) {
        String sql = "SELECT * FROM " + tableName + QueryUtils.whereClause(filters);
        return jdbcTemplate.query(sql, filters, BeanPropertyRowMapper.newInstance(clazz));
    }

    @Override
    public <T> List<T> query(String sql, Map<String, Object> params, Class<T> clazz) {
        return jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(clazz));
    }
}
