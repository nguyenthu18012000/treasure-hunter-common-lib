package com.TreasureHunter.CommonLib.response;

import com.TreasureHunter.CommonLib.util.TraceContextUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    private String code;
    private String message;
    private String traceId;
    private Instant requestTime;
    private T data;

    public BaseResponse(String code, String message, T data) {
        this(
                code,
                message,
                TraceContextUtil.getOrCreateTraceId(),
                TraceContextUtil.getRequestTime(),
                data
        );
    }

    public BaseResponse(String status, String message) {
        this(status, message, null);
    }
}
