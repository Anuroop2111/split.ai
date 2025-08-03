package com.split.ai.split.service.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    public static void validateSuggestUserQuery(String query) {
        if (query.length() < 2) {
            throw new RuntimeException("query length can't be less than 2");
        }

    }
}
