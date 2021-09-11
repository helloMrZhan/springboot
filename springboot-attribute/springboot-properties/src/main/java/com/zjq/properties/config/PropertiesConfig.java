package com.zjq.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zjq
 * @date 2021/9/11 23:03
 * <p>title:</p>
 * <p>description:</p>
 *
 *要使用@ConfigurationProperties需要在主类指定@EnableConfigurationProperties
 */

@ConfigurationProperties(prefix = "zjq")
@PropertySource("classpath:zjq.properties")
@Configuration
@Data
public class PropertiesConfig {

    private String name;

    private Integer age;

    private String feature;
}
