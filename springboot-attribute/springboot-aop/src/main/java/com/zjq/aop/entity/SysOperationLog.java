package com.zjq.aop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * <p>系统操作日志</p>
 *
 * @author zjq
 * @date 2021/8/6
 */
@Data
@EqualsAndHashCode()
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 45468787486913L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "日志类型 1-正常日志 9-异常日志")
    private Integer type;

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "服务ID")
    private String serviceId;

    @ApiModelProperty(value = "操作IP地址")
    private String ip;

    @ApiModelProperty(value = "IP地理位置")
    private String ipLocation;

    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    @ApiModelProperty(value = "请求URI")
    private String requestUri;

    @ApiModelProperty(value = "操作方式")
    private String method;

    @ApiModelProperty(value = "操作提交的数据")
    private String params;

    @ApiModelProperty(value = "执行时间")
    private Long time;

    @ApiModelProperty(value = "异常信息")
    private String exception;

}
