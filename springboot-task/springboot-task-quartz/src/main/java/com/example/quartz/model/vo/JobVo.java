package com.example.quartz.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zjq
 * @Description:
 **/
@Data
public class JobVo {

    @ApiModelProperty(value = "任务名")
    private String name;

    /**
     * 目标字符串
     * 格式bean.method(params)
     * String字符串类型，包含'、boolean布尔类型，等于true或者false
     * long长整形，包含L、double浮点类型，包含D、其他类型归类为整形
     * aa.aa('String',100L,20.20D)
     */
    @ApiModelProperty(value = "目标字符串")
    private String invokeTarget;

    @ApiModelProperty(value = "表达式")
    private String cronExpression;

    @ApiModelProperty(value = "周期(month、week、day、hour、minute、seconds)")
    private String cycle;

    @ApiModelProperty(value = "执行情况(1-执行中,2-已暂停)")
    private Integer situation;

    @ApiModelProperty(value = "下次执行时间")
    private List<String> next;
}
