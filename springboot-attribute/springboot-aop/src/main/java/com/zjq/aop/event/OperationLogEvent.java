package com.zjq.aop.event;

import com.zjq.aop.entity.SysOperationLog;
import org.springframework.context.ApplicationEvent;

/**
 * <p>系统操作日志事件</p>
 *
 * @author zjq
 * @date 2021/6/6
 */
public class OperationLogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 838536973790658353L;

    public OperationLogEvent(SysOperationLog operationLog) {
        super(operationLog);
    }
}
