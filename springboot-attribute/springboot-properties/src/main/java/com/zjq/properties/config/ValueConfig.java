package com.zjq.properties.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Value注入配置
 * @author zjq
 */
@Configuration
@Data
public class ValueConfig {

    @Value("${my.name}")
    private String name;

    @Value("${my.address}")
    private String address;


    @Value("${my.feature}")
    private String feature;
}