package com.zjq.properties.controller;

import com.zjq.properties.config.CustomPropertiesConfig;
import com.zjq.properties.config.RedisConfig;
import com.zjq.properties.config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>配置文件</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@RestController
@RequestMapping(value = "/config")
public class PropertiesController {

    @Autowired
    private CustomPropertiesConfig customPropertiesConfig;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private YmlConfig ymlConfig;

    @GetMapping("/prop")
    public String prop() {
        return customPropertiesConfig.toString();
    }

    @GetMapping("/redis")
    public String redis() {
        return redisConfig.toString();
    }

    @GetMapping("/yml")
    public String yml() {
        return ymlConfig.toString();
    }

}
