package com.example.quartz;

import com.example.quartz.job.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author zjq
 */
@Component
@Slf4j
public class MyApplicationContext implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(MyApplicationContext.class);
    private static ApplicationContext applicationContext;


    /**
     * 得到spring的IOC容器
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MyApplicationContext.applicationContext == null) {
            MyApplicationContext.applicationContext = applicationContext;
        }
        logger.info("ApplicationContext配置成功,applicationContext对象：" + MyApplicationContext.applicationContext);
    }

    /**
     * 通过bean名称获取bean实例
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 通过bean类型获取bean实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}