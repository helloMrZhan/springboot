package com.zjq.properties.config;

import com.zjq.properties.entity.Goods;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * application.yml 配置案例
 * @author zjq
 * @date 2021/9/11 23:01
 * <p>title:</p>
 * <p>description:
 *  @ConfigurationProperties 将配置文件中以zjqProp开头的属性通过setXxx方法注入到该类的属性中
 *  @Component  把PropertiesConfig类作为Bean到spring容器中，只有这样才能@ConfigurationProperties注解进行赋值
 * </p>
 */
@ConfigurationProperties(prefix = "myrandom")
@Component
@Data
public class RandomConfig {

    private String secret;
    private Integer number;
    private Long bignumber;
    private String uuid;
}
