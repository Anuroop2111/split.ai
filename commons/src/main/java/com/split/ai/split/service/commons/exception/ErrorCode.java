package com.split.ai.split.service.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumeration of application specific error codes.
 */
public enum ErrorCode {
    GENERIC_ERROR(1000, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    DATABASE_ERROR(1002, "Database operation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND(1003, "Requested entity not found", HttpStatus.NOT_FOUND),
    INVALID_QUERY(1004, "Invalid query", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_OPERATION(1005, "User lacks required role", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS(1006, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    INVALID_EXPENSE_UPDATE(1007, "Invalid expense update request", HttpStatus.BAD_REQUEST),
    PASSWORD_HASH_FAILED(1008, "Could not hash password", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ERROR_CODE(1009, "Unknown error code", HttpStatus.INTERNAL_SERVER_ERROR);

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
     * @throws SplitException if no match exists
     */
    public static ErrorCode fromCode(Integer code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.errorCode.equals(code)) {
                return errorCode;
            }
        }
        throw SplitException.createException(ErrorCode.INVALID_ERROR_CODE);
    }
}
