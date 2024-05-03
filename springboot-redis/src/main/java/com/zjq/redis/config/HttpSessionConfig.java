package com.zjq.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 2024-05
 * @author 共饮一杯无
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 7)
public class HttpSessionConfig {
}
