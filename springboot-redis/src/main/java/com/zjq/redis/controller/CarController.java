package com.zjq.redis.controller;

import com.zjq.redis.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjq
 * @date 2020/3/6 22:54
 * <p>title:</p>
 * <p>company:zjq</p>
 * <p>description:</p>
 */
@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/get")
    public Map getCar() {
        Map map = new HashMap(16);
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("hello","world");
        String hello = (String) valueOperations.get("hello");
        map.put("stringRedisTemplate",hello);
        ValueOperations valueOperations1 = redisTemplate.opsForValue();
        Car car = new Car();
        car.setId(111);
        car.setName("法拉利");
        car.setColor("红色");
        valueOperations1.set("car",car);
        Car car1 = (Car) valueOperations1.get("car");
        map.put("redisTemplate",car1);
        return map;
    }


    @RequestMapping(value = "/set")
    public void setCar() {
        // redis Set新增和查询案例

    }


}
