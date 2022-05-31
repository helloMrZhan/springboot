package com.zjq.properties.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 *  定义配置类
 * @author zjq
 */
@Configuration // 定义该类是一个配置类
public class MyConfig {
    @Bean // 将返回值对象作为组件添加到spring容器中，该组件id默认为方法名
    public MyService myService() {

        return new MyService();
    }
}