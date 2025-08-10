package com.split.ai.split.service.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumeration of application specific error codes.
 */
public enum ErrorCode {
    GENERIC_ERROR(1000, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND);

    private final Integer errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    ErrorCode(Integer errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Resolve an {@link ErrorCode} from an integer error code.
     *
     * @param code the code to resolve
     * @return matching {@link ErrorCode}
     * @throws IllegalArgumentException if no match exists
     */
    public static ErrorCode fromCode(Integer code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.errorCode.equals(code)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}
