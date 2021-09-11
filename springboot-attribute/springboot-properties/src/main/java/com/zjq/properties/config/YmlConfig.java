package com.zjq.properties.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zjq
 * @date 2021/9/11 23:01
 * <p>title:</p>
 * <p>description:</p>
 */
@Configuration
@Data
public class YmlConfig {

    @Value("${my.name}")
    private String name;

    @Value("${my.address}")
    private String address;


    @Value("${my.feature}")
    private String feature;
}
