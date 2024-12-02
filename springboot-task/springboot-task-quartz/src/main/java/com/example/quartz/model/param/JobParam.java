package com.example.quartz.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zjq
 * @Description: 新增修改类
 **/
@Data
public class JobParam {
    @ApiModelProperty(value = "任务id")
    private String id;

    @ApiModelProperty(value = "任务名")
    private String name;

    @ApiModelProperty(value = "状态（0-正常 1-禁用）")
    private Integer status;

    @ApiModelProperty(value = "执行策略(1手动，2-自动)")
    private Integer policy;


    /**
     * 目标字符串
     * 格式bean.method(params)
     * String字符串类型，包含'、boolean布尔类型，等于true或者false
     * long长整形，包含L、double浮点类型，包含D、其他类型归类为整形
     * aa.aa('String',100L,20.20D)
     */
    @ApiModelProperty(value = "目标字符串")
    private String invokeTarget;


    @ApiModelProperty(value = "周期(month、week、day、hour、minute、seconds)")
    private String cycle;

    /**
     * 特殊处理:选择周 就不能选月、日
     */
    @ApiModelProperty(value = "按周,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String week;

    /**
     * 选择月就不能选择周
     */
    @ApiModelProperty(value = "按月,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String month;

    /**
     * 选择日不能选周,并且默认为每月
     */
    @ApiModelProperty(value = "按日,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String day;

    @ApiModelProperty(value = "按时,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String hour;

    @ApiModelProperty(value = "按分,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String minute;

    @ApiModelProperty(value = "按秒,*:所有,0/n:每n周期执行,m-n:周期区间执行")
    private String seconds;
}
