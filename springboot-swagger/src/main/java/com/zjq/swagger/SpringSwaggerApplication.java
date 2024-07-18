package com.zjq.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringSwagger案例
 * @author zjq
 */
@SpringBootApplication
@EnableSwagger2
public class SpringSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSwaggerApplication.class, args);
	}

}
