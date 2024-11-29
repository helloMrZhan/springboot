package com.zjq.task.base.job;

import lombok.extern.slf4j.Slf4j;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer定时任务
 * @author 11876
 */
@Slf4j
public class TimerTest {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("doSomething...");
            }
        },2000,3000);
    }
}