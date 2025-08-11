package com.split.ai.split.service.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    public static void validateSuggestUserQuery(String query) {
        if (Objects.isNull(query) || query.length() < 2) {
            throw SplitException.createException(ErrorCode.INVALID_QUERY);
        }
    }
}
