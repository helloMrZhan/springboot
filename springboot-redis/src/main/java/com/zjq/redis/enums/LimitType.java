package com.zjq.redis.enums;

/**
 * 限流策略
 * @author 共饮一杯无
 */
public enum LimitType {

    /**
     * 默认策略
     */
    DEFAULT,

    /**
     * 根据IP进行限流
     */
    IP
}
