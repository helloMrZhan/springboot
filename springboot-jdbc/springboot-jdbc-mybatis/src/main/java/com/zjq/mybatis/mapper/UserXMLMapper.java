package com.zjq.mybatis.mapper;

import com.zjq.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>用户mapper</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Component
@Mapper
public interface UserXMLMapper {

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int add(User user);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
    int update(User user);


	/**
	 * 查询用户列表
	 * @return
	 */
	List<User> list();

	/**
	 * 通过id删除用户
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * 通过id查询用户
	 * @param id
	 * @return
	 */
	User getById(Long id);

	/**
	 * 获取所有用户和订单信息
	 * @return
	 */
    List<User> findAllUserAndOrders();
}
