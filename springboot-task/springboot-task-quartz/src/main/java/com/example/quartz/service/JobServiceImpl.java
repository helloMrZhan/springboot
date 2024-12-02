package com.example.quartz.service;


import com.example.quartz.job.JobManager;
import com.example.quartz.mapper.JobMapper;
import com.example.quartz.model.constant.JobConstant;
import com.example.quartz.model.entity.JobEntity;
import com.example.quartz.model.entity.JobLog;
import com.example.quartz.model.param.JobParam;
import com.example.quartz.model.vo.JobVo;
import com.example.quartz.utils.CronUtils;
import com.example.quartz.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zjq
 * @Description:
 **/
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private JobManager jobManager;

    /***
     *任务列表查询
     * @return o
     */
    @Override
    public Object jobList() {
        return jobMapper.jobList(null);
    }

    /**
     * 新增列表
     *
     * @param param 新增参数
     */
    @Override
    public void addJob(JobParam param) {
        // 解析表达式，此表达式由后端根据规则进行解析，可以直接由前端进行传递
        String cron = CronUtils.dateConvertToCron(param);

        //查询执行周期
        Date nextTime = CronUtils.nextCurrentTime(cron);
        //生成实体
        JobEntity job = new JobEntity();
        BeanUtils.copyProperties(param, job);
        job.setId(UUIDUtils.getUuId());
        job.setDelFlag(0);
        job.setCronExpression(cron);
        job.setNextRunTime(nextTime);
        // 执行策略(1手动-暂停状态(2)，2-自动-执行中状态(1))
        Integer situation = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.PAUSE : JobConstant.EXECUTE;
        job.setSituation(situation);
        //设置版本好为0
        job.setVersion(0);

        job.setCreateBy("");
        job.setCreateTime(new Date());
        job.setUpdateBy("");
        job.setUpdateTime(new Date());

        // 执行任务
        String runType = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.USER_RUN : JobConstant.SYSTEM_RUN;
        jobManager.startJob(job, runType);

        //插入数据库
        jobMapper.insert(job);
    }

    /**
     * 修改任务
     *
     * @param param 修改参数
     */
    @Override
    public void updateJob(JobParam param) {
        JobEntity job = jobMapper.selectById(param.getId());
        if (job == null) {
            throw new RuntimeException("更新失败,任务不存在");
        }

        //解析表达式
        String cron = CronUtils.dateConvertToCron(param);
        //查询执行周期
        Date nextTime = CronUtils.nextCurrentTime(cron);
        //生成实体
        BeanUtils.copyProperties(param, job);
        job.setCronExpression(cron);
        job.setNextRunTime(nextTime);
        // 执行策略(1手动-暂停状态(2)，2-自动-执行中状态(1))
        int situation = param.getPolicy().equals(JobConstant.MANUAL) ? JobConstant.PAUSE : JobConstant.EXECUTE;
        job.setSituation(situation);
        job.setUpdateBy("");
        job.setUpdateTime(new Date());


        // 执行策略(1手动，2-自动)：根据手动还是自动决定如何处理 执行任务
        if (job.getPolicy().equals(JobConstant.MANUAL)) {
            // 手动模式则从quartz中删除
            log.info("======== 手动模式,删除执行中任务 ========");
            jobManager.deleteJob(job.getId());
            return;
        }

        // 更新任务
        jobManager.updateJob(job);

        // 更新数据库
        jobMapper.update(job);
    }


    /**
     * 执行任务
     *
     * @param id 任务id
     */
    @Override
    public void runOnceJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("执行失败,任务不存在");
        }

        // 执行
        jobManager.runOnceJob(id);
    }

    /**
     * 暂停任务
     *
     * @param id 任务id
     */
    @Override
    public void pauseJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("暂停任务失败,任务不存在");
        }

        job.setSituation(JobConstant.PAUSE);

        // 暂停
        jobManager.pauseJob(id);

        // 更新数据库
        jobMapper.update(job);
    }

    /**
     * 恢复任务
     *
     * @param id 任务id
     */
    @Override
    public void resumeJob(String id) {
        // 更新数据库
        JobEntity job = jobMapper.selectById(id);
        if (job == null) {
            throw new RuntimeException("暂停任务失败,任务不存在");
        }
        job.setStatus(JobConstant.ENABLE);
        job.setSituation(JobConstant.EXECUTE);
        // 启动任务
        jobManager.resumeJob(job);

        // 更新数据库
        jobMapper.update(job);
    }

    /**
     * 删除任务
     *
     * @param id 任务id
     */
    @Override
    public void deleteJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("删除任务失败,任务不存在");
        }
        // 删除执行的任务
        jobManager.deleteJob(id);

        //数据库删除
        jobMapper.delete(id);
    }

    /**
     * 禁用任务
     *
     * @param id 任务id
     */
    @Override
    public void forbidJob(String id) {
        JobEntity job = jobMapper.selectById(id);

        if (job == null) {
            throw new RuntimeException("禁用失败,任务不存在");
        }

        // 删除执行的任务
        jobManager.deleteJob(id);

        //禁用
        job.setStatus(JobConstant.DISABLE);
        job.setSituation(JobConstant.PAUSE);
        jobMapper.update(job);
    }

    /**
     * 查询详情
     *
     * @param id 任务id
     */
    @Override
    public JobVo getJobById(String id) {
        JobEntity job = jobMapper.selectById(id);
        if (job == null) {
            throw new RuntimeException("任务不存在！");
        }
        JobVo jobVo = new JobVo();
        BeanUtils.copyProperties(job, jobVo);
        List<String> nextExecution = (List<String>) CronUtils.getNextExecution(job.getCronExpression(), 8, true);
        jobVo.setNext(nextExecution);
        return jobVo;
    }

    /**
     * 任务日志
     */
    @Override
    @Async
    public void insertTaskLog(JobLog log) {
        jobMapper.insertTaskLog(log);
    }
}
