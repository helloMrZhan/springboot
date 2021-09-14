package com.zjq.properties.service;

import com.zjq.properties.controller.AsyncSyncController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zjq
 * @date 2021/9/14 21:19
 * <p>title:</p>
 * <p>description:</p>
 */
@Service
public class AsyncSyncService {


    private final Logger log = LoggerFactory.getLogger(AsyncSyncController.class);

    /**
     * 同步方法
     */
    public void sync() {
        sleep();
    }

    /**
     * 异步方法
     */
    @Async(value = "threadPoolTaskExecutor")
    public void async() {
        log.info("异步方法内部线程---id={},name={}",Thread.currentThread().getId(),
                Thread.currentThread().getName());
        sleep();
    }

    public void sleep(){
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
