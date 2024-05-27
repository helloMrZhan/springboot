package com.zjq.mybatis.service.impl;

import com.google.common.collect.Sets;
import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.mapper.OrderMapper;
import com.zjq.mybatis.mapper.UserMapper;
import com.zjq.mybatis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>订单service</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;


	@Override
	public User getUserByOrderId(Long id) {
		return orderMapper.getUserByOrderId(id);
	}
}
