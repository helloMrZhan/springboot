package com.zjq.redis.exception;

import com.zjq.redis.common.SystemResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author  共饮一杯无
 * @date 2024/5/13 16:22
 * @description: 全局异常捕获处理
 */
 @RestControllerAdvice
public class GlobalExceptionAdvice {
	
	@ExceptionHandler(BadRequestException.class)
    public SystemResult<Object> serviceException(BadRequestException e) {
        return SystemResult.fail(e.getMessage());
    }

}