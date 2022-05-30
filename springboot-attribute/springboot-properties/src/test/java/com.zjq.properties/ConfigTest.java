package com.zjq.properties;

import com.zjq.properties.config.PropertiesConfig;
import com.zjq.properties.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) // 测试启动类，并加载Spring Boot测试注解
@SpringBootTest //标记为SpringBoot测试类，并加载ApplicationContext上下文环境
public class ConfigTest {

    @Autowired
    private PropertiesConfig propertiesConfig;
    @Test
    public void propertiesConfigTest(){
        System.out.println(propertiesConfig);
    }
}
