package com.example.quartz;

import com.example.quartz.job.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zjq
 * @Description: 启动完成后加载
 **/
@Component
@Slf4j
public class QuartzDemoApplicationRunner implements ApplicationRunner {
    @Resource
    private JobManager jobManager;

    @Override
    public void run(ApplicationArguments args) throws SchedulerException {
        log.info("==== 系统运行开始 ====");

        // 初始化任务
        jobManager.initTask();
    }
}
