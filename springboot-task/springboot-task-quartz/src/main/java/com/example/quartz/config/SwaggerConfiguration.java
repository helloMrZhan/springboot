package com.example.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * @author zjq
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    @Bean("quartz")
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.quartz.controller"))
                .paths(PathSelectors.any())

                .build();
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("quartz")
                .description("任务平台")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}