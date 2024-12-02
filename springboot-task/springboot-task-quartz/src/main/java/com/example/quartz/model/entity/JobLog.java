package com.example.quartz.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zjq
 * @Description:
 **/
@Data
public class JobLog {
    private String id;

    private String taskId;

    private Long time;

    /**
     * 执行状态（0正常 1失败）
     */
    private Integer status;

    private String exceptionInfo;

    private Date createTime;
}
