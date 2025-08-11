package com.split.ai.split.service.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ValidationUtil {

    public static void validateSuggestUserQuery(String query) {
        if (Objects.isNull(query) || query.length() < 2) {
            log.error("[ValidationUtil : validateSuggestUserQuery] : invalid query {}", query);
            throw SplitException.createException(ErrorCode.INVALID_QUERY);
        }
    }
}
