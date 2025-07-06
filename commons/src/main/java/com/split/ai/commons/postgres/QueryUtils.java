package com.split.ai.commons.postgres;

import java.util.Map;
import java.util.StringJoiner;

/** Utility methods for building SQL queries. */
final class QueryUtils {
    private QueryUtils() {}

    static String whereClause(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");
        filters.keySet().forEach(key -> joiner.add(key + " = :" + key));
        return joiner.toString();
    }

    static String updateAssignments(Map<String, Object> values) {
        StringJoiner joiner = new StringJoiner(", ");
        values.keySet().forEach(k -> joiner.add(k + " = :" + k));
        return joiner.toString();
    }
}
