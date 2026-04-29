package com.TreasureHunter.CommonLib.util;

import com.TreasureHunter.CommonLib.constant.CommonConstant;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.UUID;

public final class TraceContextUtil {

    private TraceContextUtil() {
    }

    public static String getOrCreateTraceId() {
        String traceId = MDC.get(CommonConstant.MDC_KEY.TRACE_ID);
        if (traceId != null && !traceId.isBlank()) {
            return traceId;
        }
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Instant getRequestTime() {
        return Instant.now();
    }
}



