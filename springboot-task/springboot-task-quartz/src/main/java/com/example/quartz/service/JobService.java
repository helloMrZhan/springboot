package com.example.quartz.service;


import com.example.quartz.model.entity.JobLog;
import com.example.quartz.model.param.JobParam;
import com.example.quartz.model.vo.JobVo;

/**
 * @author zjq
 * @Description:
 **/
public interface JobService {

    /***
     *任务列表查询
     * @return o
     */
    Object jobList();

    /**
     * 新增任务
     *
     * @param param 新增参数
     */
    void addJob(JobParam param);

    /**
     * 修改任务
     *
     * @param param 修改参数
     */
    void updateJob(JobParam param);

    /**
     * 执行任务
     *
     * @param id 任务id
     */
    void runOnceJob(String id);


    /**
     * 暂停任务
     *
     * @param id 任务id
     */
    void pauseJob(String id);


    /**
     * 恢复任务
     *
     * @param id 任务id
     */
    void resumeJob(String id);

    /**
     * 删除任务
     *
     * @param id 任务id
     */
    void deleteJob(String id);

    /**
     * 禁用任务
     *
     * @param id 任务id
     */
    void forbidJob(String id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    JobVo getJobById(String id);

    /**
     * 任务日志
     *
     * @param log
     */
    void insertTaskLog(JobLog log);
}
