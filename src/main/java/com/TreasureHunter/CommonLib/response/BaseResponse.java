package com.TreasureHunter.CommonLib.response;

import com.TreasureHunter.CommonLib.util.TraceContextUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BaseResponse<T> {
    private String code;
    private String message;
    private String traceId;
    private Instant requestTime;
    private T data;

    public BaseResponse(String code, String message, String traceId, Instant requestTime, T data) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
        this.requestTime = requestTime;
        this.data = data;
    }

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
