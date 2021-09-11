package com.zjq.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjq.mybatisplus.entity.User;

import java.util.List;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
public interface UserService extends IService<User> {

    List<User> getMasterAllUser();
}
