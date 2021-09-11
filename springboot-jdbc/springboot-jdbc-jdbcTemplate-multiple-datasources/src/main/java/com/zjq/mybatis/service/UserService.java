package com.zjq.mybatis.service;

import com.zjq.mybatis.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
public interface UserService {

    /**
     * 获取主数据库所有用户
     * @return
     */
    List<User> getMasterAllUser();
    /**
     * 获取从数据库所有用户
     * @return
     */
    List<User> getSlaveAllUser();
}
