package com.example.quartz.mapper;

import com.example.quartz.model.entity.JobEntity;
import com.example.quartz.model.entity.JobLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zjq
 * @Description:
 **/
@Mapper
public interface JobMapper {
    /**
     * 任务列表
     *
     * @return
     */
    List<JobEntity> jobList(@Param("status") Integer status);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    JobEntity selectById(String id);

    /**
     * 新增
     *
     * @param job
     * @return
     */
    int insert(JobEntity job);

    /**
     * 更新
     *
     * @param job
     * @return
     */
    int update(JobEntity job);

    /**
     * 更新版本号
     *
     * @param job
     * @param version
     * @return
     */
    int updateVersion(@Param("job") JobEntity job, @Param("version") Integer version);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 插入任务日志
     *
     * @param log
     */
    void insertTaskLog(JobLog log);

    /**
     * 更新运行状态
     *
     * @param ids
     * @param situation
     */
    void updateSituationStatus(@Param("ids") List<String> ids, @Param("situation") Integer situation);
}
