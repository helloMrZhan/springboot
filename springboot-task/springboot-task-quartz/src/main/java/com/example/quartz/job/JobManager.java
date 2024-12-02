package com.example.quartz.job;

import com.example.quartz.model.constant.JobConstant;
import com.example.quartz.model.entity.JobEntity;
import com.example.quartz.mapper.JobMapper;
import com.example.quartz.utils.CronUtils;
import com.example.quartz.utils.QuartzUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zjq
 * @Description:
 **/
@Component
@Slf4j
public class JobManager {

    @Resource
    private Scheduler scheduler;

    @Resource
    private JobMapper jobMapper;

    /**
     * 初始化任务
     */
    public void initTask() throws SchedulerException {
        // 读取自动执行任务列表状态为：正常(启用)
        List<JobEntity> jobs = jobMapper.jobList(0);
        // 过滤状态为执行中的任务
        List<JobEntity> startList = jobs.stream().filter(t -> t.getPolicy().equals(JobConstant.AUTO) || t.getSituation().equals(JobConstant.EXECUTE)).collect(Collectors.toList());
        List<String> startIdList = startList.stream().map(JobEntity::getId).collect(Collectors.toList());
        // 获取quartz中的任务,并且对比,如果存在于quartz中则删除(避免自启)
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<String> existsIdList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            String id = jobKey.getName();
            if (!startIdList.contains(id)) {
                deleteJob(id);
            } else {
                existsIdList.add(id);
            }
        }

        // 执行任务
        for (JobEntity entity : startList) {
            // 比对cron是否一致,如果不一致则删除，如果一致则不做处理
            TriggerKey triggerKey = TriggerKey.triggerKey(entity.getId());
            if (existsIdList.contains(triggerKey.getName())) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                String cronExpression = trigger.getCronExpression();
                String entityCron = entity.getCronExpression();
                if (!cronExpression.equals(entityCron)) {
                    deleteJob(entity.getId());
                    // 删除后再次执行
                    startJob(entity, JobConstant.SYSTEM_RUN);
                }
            } else {
                startJob(entity, JobConstant.SYSTEM_RUN);
            }
        }
        // 开启所有任务
        scheduler.resumeAll();
        scheduler.start();
    }

    public void destroyTask() throws SchedulerException {
        log.info("######## 结束任务 #########");

        // 查询运行中的任务，进行停止操作
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<String> ids = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            String id = jobKey.getName();
            // 暂停任务
            pauseJob(id);
            ids.add(id);
        }

        // 更新运行状态为暂停
        if (ids.size() > 0) {
            jobMapper.updateSituationStatus(ids, JobConstant.PAUSE);
        }
    }

    /**
     * 开启一个定时任务
     *
     * @param jobEntity 任务实体
     * @throws Exception
     */
    public void startJob(JobEntity jobEntity, String runType) {
        try {
            Integer status = jobEntity.getStatus();
            Integer policy = jobEntity.getPolicy();
            // 任务暂停(禁用)状态则不执行后续操作
            if (status.equals(JobConstant.DISABLE)) {
                throw new RuntimeException("禁用状态无法执行");
            }
            // 如果人不是自启、并且不是系统自动触发则不会被执行
            if (!policy.equals(JobConstant.AUTO) && !runType.equals(JobConstant.SYSTEM_RUN)) {
                return;
            }

            String jobId = jobEntity.getId();
            // 判断是否存在
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                throw new RuntimeException("任务已经存在!");
            }

            log.info("======== 创建任务：{} ========", jobId);

            // 构建参数
            JobDataMap jobDataMap = new JobDataMap();
            Map<String, Object> map = QuartzUtils.generatorJobDataMap(jobEntity);
            jobDataMap.put("paramMap", map);
            // 以任务的ID来构建定时任务
            Class<? extends Job> aClass = BaseQuartzJobBean.class;
            JobBuilder jobBuilder = JobBuilder.newJob(aClass).withIdentity(jobId);
            jobBuilder.setJobData(jobDataMap);
            JobDetail jobDetail = jobBuilder.build();

            // 设置cron
            String cron = jobEntity.getCronExpression();
            // 验证cron
            CronUtils.isValid(cron);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);

            // 更新任务状态为已执行
            // 执行中
            jobEntity.setSituation(JobConstant.EXECUTE);
            jobMapper.update(jobEntity);
        } catch (Exception e) {
            log.error("任务启动失败:{}", e);
            throw new RuntimeException("任务启动失败!");
        }
    }


    /**
     * 重新执行任务 - 更新任务
     *
     * @param jobEntity 任务实体
     * @throws SchedulerException
     */
    public void updateJob(JobEntity jobEntity) {
        try {
            String jobId = jobEntity.getId();
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            // 任务原有没有执行，则重新创建任务
            if (!exists) {
                log.info("======== 更新的任务暂未运行,将创建新任务 ========");
                startJob(jobEntity, JobConstant.SYSTEM_RUN);
                return;
            }

            log.info("======== 更新任务：{} ========", jobId);

            // 先删除，再新建
            deleteJob(jobId);
            // 新增
            String runType = jobEntity.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.USER_RUN : JobConstant.SYSTEM_RUN;
            startJob(jobEntity, runType);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务更新失败！");
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobId 定时任务名称
     * @throws SchedulerException
     */
    public void deleteJob(String jobId) {
        try {
            log.info("删除任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务删除失败！ ");
        }
    }


    /**
     * 暂停任务
     *
     * @param jobId 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void pauseJob(String jobId) {
        try {
            log.info("暂停任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (!exists) {
                log.info("任务未运行!");
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务暂停失败：" + e.getMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param job 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void resumeJob(JobEntity job) {
        String jobId = job.getId();
        // 恢复任务时会立即执行一次任务
        log.info("恢复任务：" + jobId);
        try {
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);

            if (exists) {
                scheduler.resumeJob(jobKey);
            } else {
                startJob(job, JobConstant.SYSTEM_RUN);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务恢复失败：" + e.getMessage());
        }
    }

    /**
     * 立即执行一次任务 : 没有达到任务的执行周期直接触发
     *
     * @param jobId 定时任务ID，与构造任务时的ID一致
     * @throws SchedulerException
     */
    public void runOnceJob(String jobId) {
        try {
            log.info("立即执行一次任务：" + jobId);
            JobKey jobKey = JobKey.jobKey(jobId);
            boolean exists = scheduler.checkExists(jobKey);
            if (!exists) {
                throw new RuntimeException("任务未运行!");
            }
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("任务执行失败：" + e.getMessage());
        }
    }
}
