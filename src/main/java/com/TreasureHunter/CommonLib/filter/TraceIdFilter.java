package com.TreasureHunter.CommonLib.filter;

import com.TreasureHunter.CommonLib.constant.CommonConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter để đọc traceId từ HTTP header X-Trace-Id
 * - Nếu request có header X-Trace-Id → dùng luôn
 * - Nếu không → sinh UUID mới
 * - Set vào MDC để các component khác dùng
 * - Set vào response header để client biết traceId
 */
@Component
public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Đọc traceId từ header (nếu có)
        String traceId = request.getHeader(CommonConstant.HTTP_HEADER.TRACE_ID);

        // 2. Nếu không có → sinh UUID mới
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }

        // 3. Set vào MDC để toàn bộ thread dùng
        MDC.put(CommonConstant.MDC_KEY.TRACE_ID, traceId);

        // 4. Set vào response header để client biết
        response.setHeader(CommonConstant.HTTP_HEADER.TRACE_ID, traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // 5. Dọn dẹp MDC khi request kết thúc
            MDC.remove(CommonConstant.MDC_KEY.TRACE_ID);
        }
    }
}



