package com.zjq.springcache.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 共饮一杯无
 * @date 2024/7/17 17:12
 * @description: 用户实体
 */
@Data
@AllArgsConstructor
@ApiModel(description = "User实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String name;


    @ApiModelProperty("用户年龄")
    private Integer age;
}