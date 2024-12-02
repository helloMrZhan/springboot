package com.example.quartz.job;

import com.example.quartz.MyApplicationContext;
import com.example.quartz.model.constant.JobConstant;
import com.example.quartz.model.entity.JobLog;
import com.example.quartz.service.JobService;
import com.example.quartz.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author zjq
 * @Description:
 **/
@Slf4j
public class JobLogRecord {

    public static void recordTaskLog(String taskId, long currentTime, String exceptionInfo) {
        long recordTime = System.currentTimeMillis();
        // 正常
        int status = JobConstant.ENABLE;

        if (!StringUtils.isEmpty(exceptionInfo)) {
            //异常
            status = JobConstant.DISABLE;
            exceptionInfo = exceptionInfo.length() > 2000 ? exceptionInfo.substring(0, 2000) : exceptionInfo;
        }

        // 记录日志
        JobLog jobLog = new JobLog();
        jobLog.setId(UUIDUtils.getUuId());
        jobLog.setTaskId(taskId);
        jobLog.setStatus(status);
        if (currentTime > 0) {
            jobLog.setTime(recordTime - currentTime);
        }
        jobLog.setExceptionInfo(exceptionInfo);
        jobLog.setCreateTime(new Date());

        //插入记录
        MyApplicationContext.getBean(JobService.class).insertTaskLog(jobLog);
    }
}
