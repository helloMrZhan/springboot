package com.zjq.springcache.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类
 * @author 共饮一杯无
 */
//指定为配置类
@Configuration
public class SwaggerConfig {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("API文档标题")
                        .description("文档描述：用于实时查看、测试API")
                        .contact(new Contact("共饮一杯无", "https://zhanjq.blog.csdn.net/", ""))
                        .version("版本号:1.0")
                        .build())
                .select()
                //API基础扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.zjq.springcache"))
                .paths(PathSelectors.any())
                .build();
    }

}