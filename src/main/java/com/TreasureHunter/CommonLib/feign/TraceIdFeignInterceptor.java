package com.TreasureHunter.CommonLib.feign;

import com.TreasureHunter.CommonLib.constant.CommonConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * Feign Interceptor để tự động pass traceId khi gọi service khác
 * - Đọc traceId từ MDC (được set bởi TraceIdFilter)
 * - Thêm vào header X-Trace-Id của request Feign
 * - Service B sẽ nhận được traceId giống Service A
 */
@Component
public class TraceIdFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 1. Lấy traceId từ MDC (được set bởi TraceIdFilter)
        String traceId = MDC.get(CommonConstant.MDC_KEY.TRACE_ID);

        // 2. Nếu có traceId → thêm vào header request
        if (traceId != null && !traceId.isBlank()) {
            template.header(CommonConstant.HTTP_HEADER.TRACE_ID, traceId);
        }
    }
}


