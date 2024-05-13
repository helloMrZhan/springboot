package com.zjq.redis.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author  共饮一杯无
 * @date 2024/5/13 16:21
 * @description: 统一返回实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemResult<T>  {


    private boolean flag = false;
    private T data;
    private Integer code = 200;
    private String message;

    public static <T> SystemResult<T> success() {
        return SystemResult.success(null);
    }

    public static <T> SystemResult<T> success(T result) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(true);
        systemResult.setData(result);
        systemResult.setMessage("成功");
        return systemResult;
    }

    public static <T> SystemResult<T> success(String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(true);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(T result) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(500);
        systemResult.setData(result);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(500);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(T result, String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(500);
        systemResult.setMessage(msg);
        systemResult.setData(result);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(T result, String msg, String code) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(500);
        systemResult.setMessage(msg);
        systemResult.setData(result);
        return systemResult;
    }



}
