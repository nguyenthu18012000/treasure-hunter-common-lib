package com.TreasureHunter.CommonLib.constant;

public interface CommonConstant {
    // ==================== HTTP Headers ====================
    interface HTTP_HEADER {
        String TRACE_ID = "X-Trace-Id";
    }

    // ==================== MDC Keys ====================
    interface MDC_KEY {
        String TRACE_ID = "traceId";
    }

    // ==================== Response Codes ====================
    interface RESPONSE_CODE {
        String SUCCESS = "000";
        String VALIDATION_ERROR = "400";
        String INTERNAL_ERROR = "500";
    }

    // ==================== Response Messages ====================
    interface RESPONSE_MESSAGE {
        String SUCCESS = "Success";
        String VALIDATION_FAILED = "Validation failed";
        String INTERNAL_SERVER_ERROR = "Internal server error";
    }
}
