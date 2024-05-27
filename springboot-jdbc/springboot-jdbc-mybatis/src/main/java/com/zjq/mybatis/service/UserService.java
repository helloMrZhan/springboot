package com.zjq.mybatis.service;

import com.zjq.mybatis.entity.User;
import java.util.List;

/**
 * <p>用户service</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
public interface UserService {
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
     * @param userId
     * @return
     */
    int deleteById(Long userId);

    /**
     * 通过id查询用户
     * @param userId
     * @return
     */
    User getById(Long userId);


    /**
     * 获取所有用户和订单信息
     * @return
     */
    List<User> findAllUserAndOrders();
}
