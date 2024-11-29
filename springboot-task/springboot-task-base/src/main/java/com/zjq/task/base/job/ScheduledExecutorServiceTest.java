package com.zjq.task.base.job;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * ScheduledExecutorService定时任务
 * @author zjq
 */
@Slf4j
public class ScheduledExecutorServiceTest {

    public static void main(String[] args) {
        // 手动创建ScheduledThreadPoolExecutor
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
                5,
                new ThreadFactoryBuilder().setNameFormat("scheduled-pool-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("doSomething...");
        }, 1000, 3000, TimeUnit.MILLISECONDS);
    }
}