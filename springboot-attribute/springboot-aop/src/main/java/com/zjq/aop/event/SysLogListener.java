package com.zjq.aop.event;

import com.zjq.aop.entity.SysOperationLog;
import com.zjq.aop.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>异步监听系统操作日志事件</p>
 *
 * @author zjq
 * @date 2021/6/6
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

    @Autowired
    private SysLogService saveOperationLog;

    @Async
    @Order
    @EventListener(OperationLogEvent.class)
    public void saveOperationLog(OperationLogEvent event) {
        SysOperationLog log = (SysOperationLog) event.getSource();
        saveOperationLog.save(log);
    }

}
