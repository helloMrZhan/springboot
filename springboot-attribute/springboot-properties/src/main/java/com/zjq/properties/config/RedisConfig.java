package com.zjq.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zjq
 * @date 2021/9/11
 * <p>title:</p>
 * <p>description:</p>
 */
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {

    private int timeout = 3000;
    private int port;
    private int database;
    private String host;
    private String password;
    private int connectionPoolSize = 10;
    private int connectionMinimumIdleSize = 10;
}
