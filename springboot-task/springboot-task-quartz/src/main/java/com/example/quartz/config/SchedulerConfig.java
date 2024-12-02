package com.example.quartz.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author zjq
 * @Description:
 **/
@Configuration
public class SchedulerConfig {
    @Resource
    private DataSource dataSource;

    /**
     * Scheduler调度器
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        return schedulerFactoryBean().getScheduler();
    }

    @Resource
    private QuartzProperties quartzProperties;

    /**
     * Scheduler工厂类
     *
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        // 取消启动时运行任务,由自定义配置启动
        factory.setAutoStartup(false);
        factory.setSchedulerName("Quartz-Scheduler");
        // 关闭应用时，无需等待任务
        factory.setWaitForJobsToCompleteOnShutdown(false);
        // 启动时更新己存在的Job
        factory.setOverwriteExistingJobs(true);
        // 设置配置参数
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        factory.setQuartzProperties(properties);
        return factory;
    }
}
