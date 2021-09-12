package com.zjq.aop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AOP
 */
@SpringBootApplication
//这个注解必须要有不然会报错：NoSuchBeanDefinitionException: No qualifying bean of type 'com.zjq.aop.mapper.UserMapper' available
@MapperScan(basePackages = "com.zjq.aop.mapper")
public class AOPApplication {

	public static void main(String[] args) {
		SpringApplication.run(AOPApplication.class, args);
	}

}
