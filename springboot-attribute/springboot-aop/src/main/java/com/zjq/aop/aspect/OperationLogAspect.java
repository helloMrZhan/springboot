package com.zjq.aop.aspect;

import cn.hutool.core.util.StrUtil;
import com.zjq.aop.anno.OperationLog;
import com.zjq.aop.bean.RequestDetail;
import com.zjq.aop.entity.SysOperationLog;
import com.zjq.aop.enums.LogTypeEnum;
import com.zjq.aop.event.OperationLogEvent;
import com.zjq.aop.utils.ApplicationUtils;
import com.zjq.aop.utils.IpUtil;
import com.zjq.aop.utils.RequestDetailThreadLocal;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>操作日志切面</p>
 *
 * @author zjq
 * @date 2020/10/27
 */
@Slf4j
@Aspect
public class OperationLogAspect {

    @Value("${spring.application.name}")
    private String appName;

    @Around("@annotation(operationLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        // 方法开始时间
        Long startTime = System.currentTimeMillis();
        LogTypeEnum type = operationLog.logType();
        String exception = null;
        Object obj;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            type = LogTypeEnum.ERROR;
            exception = e.getMessage();
            throw e;
        } finally {
            // 结束时间
            Long endTime = System.currentTimeMillis();
            Long time = endTime - startTime;
            String title = operationLog.title();
            if (StrUtil.isBlank(title)) {
                MethodSignature signature = (MethodSignature) point.getSignature();
                ApiOperation operation = signature.getMethod().getAnnotation(ApiOperation.class);
                title = !ObjectUtils.isEmpty(operation) ? operation.value() : title;
            }
            SysOperationLog sysOperationLog = createLog(title, type, time, exception);
            ApplicationUtils.publishEvent(new OperationLogEvent(sysOperationLog));
        }

        return obj;
    }

    private SysOperationLog createLog(String title, LogTypeEnum logType, Long time, String exception) {
        HttpServletRequest request = ApplicationUtils.getRequest();

        SysOperationLog log = new SysOperationLog();
        log.setTitle(title);
        log.setType(logType.getCode());
        log.setServiceId(appName);

        String ip = IpUtil.getRequestIp();
        log.setIp(ip);
        log.setIpLocation(IpUtil.getLocation(ip));
        log.setUserAgent(request.getHeader("user-agent"));
        log.setMethod(request.getMethod());
        log.setTime(time);
        log.setException(exception);


        RequestDetail requestDetail = RequestDetailThreadLocal.getRequestDetail();
        if (requestDetail != null) {
            String params = requestDetail.getParams();
            params = StrUtil.isBlank(params) ? "" : params.replaceAll(" ", "");
            log.setParams(params);
            log.setRequestUri(requestDetail.getPath());
        }
        return log;
    }
}
