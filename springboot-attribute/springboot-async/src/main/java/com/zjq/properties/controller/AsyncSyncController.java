package com.zjq.properties.controller;

import com.zjq.properties.service.AsyncSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>配置文件</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@RestController
@RequestMapping(value = "/")
public class AsyncSyncController {

    @Autowired
    private AsyncSyncService asyncSyncService;

    private final Logger log = LoggerFactory.getLogger(AsyncSyncController.class);

    @GetMapping("/sync")
    public void sync() {
        long start = System.currentTimeMillis();
        log.info("同步方法开始。。。。。。");
        for(int i=0;i<2;i++) {
            asyncSyncService.sync();
        }
        log.info("同步方法结束。。。。。。");
        long end = System.currentTimeMillis();
        log.info("总耗时：{} ms", end - start);
    }


    @GetMapping("/async")
    public void async() {
        long start = System.currentTimeMillis();
        log.info("异步方法开始。。。。。。");
        for(int i=0;i<2;i++) {
            asyncSyncService.async();
        }
        log.info("异步方法结束。。。。。。");
        long end = System.currentTimeMillis();
        log.info("总耗时：{} ms", end - start);
    }

}
