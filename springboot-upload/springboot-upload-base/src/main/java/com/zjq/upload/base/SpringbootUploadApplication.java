package com.zjq.upload.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootUploadApplication.class, args);
	}
}
