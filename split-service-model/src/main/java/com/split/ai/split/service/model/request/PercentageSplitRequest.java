package com.split.ai.split.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * Split request based on percentage.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PercentageSplitRequest implements SplitRequest {
    @Serial
    private static final long serialVersionUID = -1028392738982734111L;

    private List<UserPercentageSplitDto> userPercentageSplitDtoList;
}
