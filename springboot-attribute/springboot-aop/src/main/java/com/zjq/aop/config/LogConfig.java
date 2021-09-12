package com.zjq.aop.config;

import com.zjq.aop.aspect.OperationLogAspect;
import com.zjq.aop.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>日志相关类注入</p>
 *
 * @author zjq
 * @date 2021/6/6
 */
@EnableAsync
@RequiredArgsConstructor
@Configuration
public class LogConfig {


    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener();
    }

    @Bean
    public OperationLogAspect operationLogAspect() {
        return new OperationLogAspect();
    }

}
