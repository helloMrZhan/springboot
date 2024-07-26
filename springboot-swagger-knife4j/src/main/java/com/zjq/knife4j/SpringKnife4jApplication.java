package com.zjq.knife4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * SpringKnife4j案例
 * @author zjq
 */
@SpringBootApplication
@EnableSwagger2WebMvc
public class SpringKnife4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKnife4jApplication.class, args);
	}

}
