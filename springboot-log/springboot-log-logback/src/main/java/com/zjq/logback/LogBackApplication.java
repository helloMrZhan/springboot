package com.zjq.logback;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "com.zjq.logback.mapper")
@Slf4j
public class LogBackApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(LogBackApplication.class, args);
		log.debug("初始化的bean数量：{}",configurableApplicationContext.getBeanDefinitionCount());
		log.info("初始化的bean数量：{}",configurableApplicationContext.getBeanDefinitionCount());
		log.error("不好！发生错误了");
	}

}
