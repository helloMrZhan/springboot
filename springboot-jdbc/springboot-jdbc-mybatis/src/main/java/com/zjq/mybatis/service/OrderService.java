package com.zjq.mybatis.service;

import com.zjq.mybatis.entity.User;

import java.util.List;

/**
 * <p>订单service</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
public interface OrderService {
    /**
     * 通过订单id查询用户
     * @param id
     * @return
     */
    User getUserByOrderId(Long id);


}
