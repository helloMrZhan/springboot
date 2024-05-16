package com.zjq.redis.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author  共饮一杯无
 * @date 2024/5/13 19:00
 * @description: RedisTemplate实现计数器
 */
@Service
public class RedisTemplateCounter {

    @Autowired
    private RedisTemplate redisTemplate;


    public void incrementCounter(String counterName) {
        redisTemplate.opsForValue().increment(counterName);
    }

    public Integer getCounterValue(String counterName) {
        return (Integer) redisTemplate.opsForValue().get(counterName);
    }

    public void setExpiration(String counterName, int seconds) {
        redisTemplate.expire(counterName, seconds, TimeUnit.SECONDS);
    }

    public void resetCounter(String counterName) {
        redisTemplate.delete(counterName);
    }
}
