package com.zjq.redis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
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

    @Test
    public void redisGetSetTest(){
        redisTemplate.opsForValue().set("username","共饮一杯无");
        String username = (String) redisTemplate.opsForValue().get("username");
        System.out.println("username="+username);
    }

}
