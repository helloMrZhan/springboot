package com.zjq.redis.constant;

import com.zjq.redis.enums.LimitType;

import java.lang.annotation.*;

/**
 * @author  共饮一杯无
 * @date 2024/5/13 13:02
 * @description:  限流注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流key
     */
    String key() default "rate_limit:";

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 50;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
