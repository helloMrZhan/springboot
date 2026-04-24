package com.zjq.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * OCR案例
 * @author zjq
 */
@SpringBootApplication
@EnableSwagger2
public class SpringOCRApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOCRApplication.class, args);
	}

}
