package com.example.quartz.job;

import com.example.quartz.MyApplicationContext;
import com.example.quartz.model.constant.JobConstant;
import com.example.quartz.model.entity.JobEntity;
import com.example.quartz.mapper.JobMapper;
import com.example.quartz.utils.CronUtils;
import com.example.quartz.utils.QuartzUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zjq
 * @Description: Quartz任务Bean，使用反射将分为分发到具体的类中进行执行
 **/

// 持久化
@PersistJobDataAfterExecution
// 禁止并发执行
@DisallowConcurrentExecution
@Component
@Slf4j
public class BaseQuartzJobBean extends QuartzJobBean {

    @Resource
    private JobManager jobManager = MyApplicationContext.getBean(JobManager.class);

    @Resource
    private JobMapper jobMapper = MyApplicationContext.getBean(JobMapper.class);

    /**
     * 是否进行校验时间差，第一次执行任务时，不校验时间差
     */
    private boolean checkTime = false;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobId = jobDetail.getKey().getName();
        log.info("执行任务，任务ID：" + jobId);

        //获取当前执行时间戳
        long currentTime = System.currentTimeMillis();

        // 获取参数
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Map<String, Object> paramMap = (Map<String, Object>) jobDataMap.get("paramMap");


        String cron = paramMap.get("cron").toString();
        Date nextCurrentTime = CronUtils.nextCurrentTime(cron);
        log.info("任务：{}，当前：{}，下次：{}", jobId, new Date(), nextCurrentTime);
        // 时间差
        long diffTime = Math.abs(currentTime - nextCurrentTime.getTime());

        //执行时，允许200ms误差，为了防止服务器时间钟摆出现误差
        if (diffTime > 200 && checkTime) {
            String msg = "任务执行异常，时间节点错误！";
            //开发中出现了错误情况，可以采用发生邮箱提醒给开发者
            log.error(msg);

            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, msg);
            return;
        }

        JobEntity job = jobMapper.selectById(jobId);
        try {
            // 模拟耗时
            TimeUnit.SECONDS.sleep(1);

            // 通过反射执行方法
            String invokeTarget = paramMap.get("beanName").toString();
            log.info("beanName：" + invokeTarget);

            String methodStr = paramMap.get("methodName").toString();
            log.info("methodName：" + methodStr);

            // 执行具体类的方法
            List<Object[]> methodParams = (List<Object[]>) paramMap.get("methodParams");
            Object beanName = MyApplicationContext.getBean(invokeTarget);
            Method method = beanName.getClass().getMethod(methodStr, QuartzUtils.getMethodParamsType(methodParams));

            // 执行任务
            method.invoke(beanName, QuartzUtils.getMethodParamsValue(methodParams));
            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, null);
        } catch (Exception e) {
            String message = "没有找到可执行方法：" + e.getMessage();
            log.error(message);
            // 需要关闭任务
            jobManager.deleteJob(jobId);
            job.setSituation(JobConstant.PAUSE);

            // 记录日志
            JobLogRecord.recordTaskLog(jobId, currentTime, message);
        } finally {
            // 当任务执行完成后，后续开启时间校验
            checkTime = true;

            // 更新任务
            job.setId(jobId);
            job.setLastRunTime(new Date());
            job.setNextRunTime(nextCurrentTime);
            jobMapper.updateVersion(job, job.getVersion());
        }
    }
}
