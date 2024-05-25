package com.zjq.mybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * 订单实体
 * @author  共饮一杯无
 * @date 2024/5/25 21:41
 * @description:
 */
@Data
public class Order {

    private long id;
    private Date ordertime;
    private double total;
    private long uid;

    /**
     * 代表当前订单从属于哪一个客户
     */
    private User user;
}