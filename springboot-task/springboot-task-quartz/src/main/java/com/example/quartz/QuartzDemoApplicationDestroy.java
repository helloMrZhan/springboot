package com.example.quartz;

import com.example.quartz.job.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zjq
 * @Description: 启动完成后加载
 **/
@Component
@Slf4j
public class QuartzDemoApplicationDestroy implements DisposableBean {

    @Resource
    private JobManager jobManager;

    @Override
    public void destroy() throws Exception {
        log.info("==== 系统运行结束 ====");
        jobManager.destroyTask();
    }

}