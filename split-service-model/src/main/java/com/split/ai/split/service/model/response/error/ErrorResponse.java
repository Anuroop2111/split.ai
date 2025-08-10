package com.split.ai.split.service.model.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Response returned when an exception occurs.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4965034822214328909L;

    private Integer errorCode;
    private String errorMessage;
    private String timestamp;
    private String serviceName;
    private String className;
    private String methodName;
    private String rootCause;
}
