package com.zjq.canal.canalsample;

import com.zjq.starter.canal.annotation.EnableCanalClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zjq
 */
@SpringBootApplication
@EnableCanalClient
@MapperScan(basePackages = "com.zjq.canal.canalsample.mapper")

public class CanalSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanalSampleApplication.class, args);
	}
}
