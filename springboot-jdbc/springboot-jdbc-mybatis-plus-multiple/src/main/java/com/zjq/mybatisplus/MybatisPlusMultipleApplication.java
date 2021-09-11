package com.zjq.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zjq
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zjq.mybatisplus.mapper")
public class MybatisPlusMultipleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusMultipleApplication.class, args);
	}

}
