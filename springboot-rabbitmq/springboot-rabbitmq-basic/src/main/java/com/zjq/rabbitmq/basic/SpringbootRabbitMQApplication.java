package com.zjq.rabbitmq.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * rabbitmq 启动类
 * @author zjq
 */
@SpringBootApplication
@EnableScheduling
public class SpringbootRabbitMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitMQApplication.class, args);
	}
}
