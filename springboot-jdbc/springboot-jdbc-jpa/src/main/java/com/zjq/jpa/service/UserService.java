package com.zjq.jpa.service;

import com.zjq.jpa.entity.User;

import java.util.List;

/**
 * <p>用户</p>
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
	User add(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    void update(User user);

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
    void deleteById(Integer userId);

    /**
     * 通过id查询用户
     * @param userId
     * @return
     */
    User getById(Integer userId);

}
