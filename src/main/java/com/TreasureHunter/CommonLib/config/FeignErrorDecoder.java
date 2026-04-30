package com.TreasureHunter.CommonLib.config;

import com.TreasureHunter.CommonLib.exception.CommonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = readBody(response);
        String message = extractMessage(body);
        String errorCode = String.valueOf(response.status());

        log.warn("[Feign error] method={}, status={}, message={}", methodKey, response.status(), message);

        return new CommonException(errorCode, message);
    }

    private String readBody(Response response) {
        if (response.body() == null) return null;
        try (InputStream is = response.body().asInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("[FeignErrorDecoder] Cannot read upstream body", e);
            return null;
        }
    }

    private String extractMessage(String body) {
        if (body == null || body.isBlank()) return "Loi khong xac dinh tu upstream service";
        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode node = root.get("message");
            if (node != null && !node.isNull() && !node.asText().isBlank()) {
                return node.asText();
            }
        } catch (Exception e) {
            log.warn("[FeignErrorDecoder] Cannot parse upstream body", e);
        }
        return "Loi khong xac dinh tu upstream service";
    }
}
