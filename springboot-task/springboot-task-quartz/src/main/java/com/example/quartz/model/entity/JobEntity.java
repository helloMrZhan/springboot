package com.example.quartz.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zjq
 * @Description:
 **/
@Data
public class JobEntity {
    /**
     * id
     */
    private String id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 目标字符串
     * 格式bean.method(params)
     * String字符串类型，包含'、boolean布尔类型，等于true或者false
     * long长整形，包含L、double浮点类型，包含D、其他类型归类为整形
     * aa.aa('String',100L,20.20D)
     */
    private String invokeTarget;

    /**
     * 周期(month、week、day、hour、minute、seconds)
     */
    private String cycle;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行策略(1手动，2-自动）
     */
    private Integer policy;

    /**
     * 状态（0-正常 1-禁用）
     */
    private Integer status;
    /**
     * 删除标志（0-存在，1-删除）
     */
    private Integer delFlag;

    /**
     * 执行情况(1-执行中,2-已暂停)
     */
    private Integer situation;

    /**
     * 执行版本(每执行一次加一)
     */
    private Integer version;

    /**
     * 上次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastRunTime;

    /**
     * 下次执行时间
     */
    private Date nextRunTime;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String updateBy;

    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}



