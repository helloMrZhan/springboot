package com.zjq.properties;

import com.zjq.properties.config.PropertiesConfig;
import com.zjq.properties.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 配置文件
 * @author zjq
 */
@EnableConfigurationProperties(value = {PropertiesConfig.class, RedisConfig.class})
@SpringBootApplication
public class PropertiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertiesApplication.class, args);
	}

}
