package com.zjq.redis.counter;

import redis.clients.jedis.Jedis;

/**
 * @author  共饮一杯无
 * @date 2024/5/13 18:47
 * @description: 通过Jedis实现计数器功能
 */
public class RedisJedisCounter {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private Jedis jedis;

    public RedisJedisCounter() {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        jedis.auth("123456");
    }

    public void incrementCounter(String counterName) {
        // 增加计数器的值
        jedis.incr(counterName);
    }

    public String getCounterValue(String counterName) {
        // 获取计数器的值
        String counterValue = jedis.get(counterName);
        return counterValue;
    }

    public void setExpiration(String counterName, long seconds) {
        // 设置计数器的过期时间
        jedis.expire(counterName, seconds);
    }

    public void resetCounter(String counterName) {
        // 重置计数器
        jedis.del(counterName);
    }

    public static void main(String[] args) {
        RedisJedisCounter counter = new RedisJedisCounter();
        counter.incrementCounter("my_counter");
        counter.incrementCounter("my_counter");
        counter.incrementCounter("my_counter");
        System.out.println("Counter value: " + counter.getCounterValue("my_counter"));
        // 设置24小时后过期
        counter.setExpiration("my_counter", 86400L);
        // ... 其他操作
    }
}
