package com.zjq.properties;

import com.zjq.properties.config.CustomPropertiesConfig;
import com.zjq.properties.config.PropertiesConfig;
import com.zjq.properties.config.ValueConfig;
import com.zjq.properties.config.YmlConfig;
import com.zjq.properties.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) // 测试启动类，并加载Spring Boot测试注解
@SpringBootTest //标记为SpringBoot测试类，并加载ApplicationContext上下文环境
public class ConfigTest {

    @Autowired
    private PropertiesConfig propertiesConfig;
    @Autowired
    private YmlConfig ymlConfig;
    @Autowired
    private ValueConfig valueConfig;
    @Autowired
    private CustomPropertiesConfig customPropertiesConfig;
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String addr;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Test
    public void propertiesConfigTest(){
        System.out.println(propertiesConfig);
    }

    @Test
    public void ymlConfigTest(){
        System.out.println(ymlConfig);
    }

    @Test
    public void valueConfigTest(){
        System.out.println(valueConfig);
    }

    @Test
    public void customPropertiesConfigTest(){
        System.out.println(customPropertiesConfig);
    }

    @Test
    public void iocTest() {
        System.out.println(applicationContext.containsBean("myService"));
    }

    @Test
    public void placeholderTest() {
        System.out.println("addr："+addr);
        System.out.println("namespace："+namespace);
    }
}
