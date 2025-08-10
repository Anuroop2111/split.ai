package com.split.ai.split.service.server.advice;

import com.split.ai.split.service.commons.exception.ErrorCode;
import com.split.ai.split.service.commons.exception.SplitException;
import com.split.ai.split.service.model.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * Global handler for {@link SplitException}.
 */
@ControllerAdvice
@Slf4j
public class SplitExceptionAdvice {

    private static final String SERVICE_NAME = "split-service";

    @ExceptionHandler(SplitException.class)
    public ResponseEntity<ErrorResponse> handleSplitException(SplitException exception) {
        log.error("[SplitExceptionAdvice : handleSplitException] : {}", exception.getMessage(), exception);
        ErrorCode errorCode = ErrorCode.fromCode(exception.getErrorCode());
        ErrorResponse response = ErrorResponse.builder()
            .errorCode(errorCode.getErrorCode())
            .errorMessage(errorCode.getErrorMessage())
            .timestamp(Instant.now().toString())
            .serviceName(SERVICE_NAME)
            .className(getClassName(exception))
            .methodName(getMethodName(exception))
            .rootCause(ExceptionUtils.getRootCauseMessage(exception))
            .build();
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    private String getClassName(Exception exception) {
        return exception.getStackTrace().length > 0 ? exception.getStackTrace()[0].getClassName() : null;
    }

    private String getMethodName(Exception exception) {
        return exception.getStackTrace().length > 0 ? exception.getStackTrace()[0].getMethodName() : null;
    }
}
