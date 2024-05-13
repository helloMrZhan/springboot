package com.zjq.redis.exception;

import lombok.Getter;

/**
 * 自定义异常
 * @author 共饮一杯无
 */
@Getter
public class BadRequestException extends RuntimeException {

    public BadRequestException(){
        super();
    }

    public BadRequestException(String msg){
        super(msg);
    }
}