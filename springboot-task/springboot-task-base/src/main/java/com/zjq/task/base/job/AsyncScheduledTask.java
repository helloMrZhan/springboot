package com.zjq.task.base.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 异步定时任务
 * @author zjq
 */
@Component
@Slf4j
@EnableAsync
public class AsyncScheduledTask {


    @Scheduled(fixedRate = 2000)
    public void runWithFixedDelay() {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("【job】开始执行：{}", DateUtil.formatDateTime(new Date()));
        } catch (InterruptedException e) {
            log.error("错误信息",e);
        }
    }

    @Async
    @Scheduled(fixedRate = 2000)
    public void asyncJob() {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("【asyncJob】开始执行：{}", DateUtil.formatDateTime(new Date()));
        } catch (InterruptedException e) {
            log.error("错误信息",e);
        }
    }
}