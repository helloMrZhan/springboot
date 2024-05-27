package com.zjq.mybatis.mapper;

import com.zjq.mybatis.entity.Order;
import com.zjq.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单mapper
 * @author  共饮一杯无
 * @date 2024/5/25 21:48
 * @description:
 */
@Component
@Mapper
public interface OrderMapper {


	/**
	 * 通过用户id查询用户
	 * @param uid
	 * @return
	 */
	User getById(Long uid);
}
