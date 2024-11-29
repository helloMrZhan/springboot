package com.zjq.task.base.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>任务列表</p>
 *
 * @Author zjq
 * @Date 2021/10/14
 */
//@Component
@Slf4j
public class ScheduledTaskJob {

    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，间隔 2s 执行
     * 固定间隔时间
     */
    @Scheduled(fixedRate = 2000)
    public void job2() {
        log.info("【job2】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，间隔 10s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 10000)
    public void job3() {
        log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，延迟 5s 后间隔 8s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 8000, initialDelay = 5000)
    public void job4() {
        log.info("【job4】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 根据cron表达式配置来执行定时任务，表达式标准参考：http://cron.qqe2.com/
     */
    @Scheduled(cron = "${zjq.task.cron}")
    public void jobCorn() {
        log.info("【jobCorn】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 根据cron表达式每天凌晨两点来执行定时任务，表达式标准参考：http://cron.qqe2.com/
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void jobCorn2() {
        log.info("【jobCorn2】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }
}
