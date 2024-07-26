package com.zjq.knife4j.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 共饮一杯无
 * @date 2024/7/17 17:12
 * @description: 部门实体
 */
@Data
@AllArgsConstructor
@ApiModel(description = "部门实体")
public class Dept {

    @ApiModelProperty("部门id")
    private Integer id;

    @ApiModelProperty("部门名称")
    private String deptname;
}
