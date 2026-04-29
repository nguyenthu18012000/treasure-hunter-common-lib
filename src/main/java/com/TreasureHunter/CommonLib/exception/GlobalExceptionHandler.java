package com.TreasureHunter.CommonLib.exception;

import com.TreasureHunter.CommonLib.constant.CommonConstant;
import com.TreasureHunter.CommonLib.response.BaseResponse;
import com.TreasureHunter.CommonLib.util.TraceContextUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<BaseResponse<?>> handleCustomException(CommonException ex) {
        BaseResponse<?> apiResponse = new BaseResponse<>(
                ex.getErrorCode(),
                ex.getMessage(),
                TraceContextUtil.getOrCreateTraceId(),
                TraceContextUtil.getRequestTime(),
                null
        );

        String logMessage = StringUtils.isEmpty(ex.getMessage()) ? "CommonException" : ex.getMessage();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace.length > 0) {
            logger.error("Class: {}, Method: {} Error: {}", stackTrace[0].getClassName(), stackTrace[0].getMethodName(), logMessage);
        } else {
            logger.error("Error: {}", logMessage);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        BaseResponse<Map<String, String>> apiResponse = new BaseResponse<>(
                CommonConstant.RESPONSE_CODE.VALIDATION_ERROR,
                CommonConstant.RESPONSE_MESSAGE.VALIDATION_FAILED,
                TraceContextUtil.getOrCreateTraceId(),
                TraceContextUtil.getRequestTime(),
                errors
        );
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleUnexpectedException(Exception ex) {
        BaseResponse<?> apiResponse = new BaseResponse<>(
                CommonConstant.RESPONSE_CODE.INTERNAL_ERROR,
                CommonConstant.RESPONSE_MESSAGE.INTERNAL_SERVER_ERROR,
                TraceContextUtil.getOrCreateTraceId(),
                TraceContextUtil.getRequestTime(),
                null
        );

        logger.error("traceId={} unexpected_error={}", apiResponse.getTraceId(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

}
