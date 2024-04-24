package com.zjq.redis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis使用案例测试
 * @author zjq
 * @date 2022-10-12
 */
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConnectTest(){
        //心跳检测连接情况
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("pong="+pong);
    }

    /**
     * Redis String类型测试
     * @throws InterruptedException
     */
    @Test
    public void redisStringGetSetTest() throws InterruptedException {
        // 设置值，默认不过期
        redisTemplate.opsForValue().set("userName","共饮一杯无");
        // 获取值
        String userName = (String) redisTemplate.opsForValue().get("userName");
        System.out.println("获取userName对应的值="+userName);

        // 设置值并且设置2秒过期时间，过期之后自动删除
        redisTemplate.opsForValue().set("verificationCode", "666888", 2, TimeUnit.SECONDS);
        Thread.sleep(1000);
        System.out.println("获取验证码过期时间（单位秒）：" + redisTemplate.getExpire("verificationCode"));
        System.out.println("获取验证码对应的值：" +  redisTemplate.opsForValue().get("verificationCode"));
        Thread.sleep(1000);
        System.out.println("获取验证码对应的值：" +  redisTemplate.opsForValue().get("verificationCode"));

        // 删除key
        Boolean result = redisTemplate.delete("userName");
        System.out.println("删除userName结果：" +  result);
    }

}
