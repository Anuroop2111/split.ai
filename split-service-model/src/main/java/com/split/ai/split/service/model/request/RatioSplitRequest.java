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
 * Split request based on ratios.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatioSplitRequest implements SplitRequest {
    @Serial
    private static final long serialVersionUID = -171283492872883491L;

    private List<UserRatioSplitDto> userRatioSplitDtoList;
}
