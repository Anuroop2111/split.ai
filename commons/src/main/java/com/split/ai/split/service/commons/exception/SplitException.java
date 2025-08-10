package com.split.ai.split.service.commons.exception;

/**
 * Custom runtime exception carrying application specific error details.
 */
public class SplitException extends RuntimeException {

    private final Integer errorCode;
    private final String message;

    private SplitException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public static SplitException createException(ErrorCode errorCode) {
        return new SplitException(errorCode.getErrorCode(), errorCode.getErrorMessage());
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
