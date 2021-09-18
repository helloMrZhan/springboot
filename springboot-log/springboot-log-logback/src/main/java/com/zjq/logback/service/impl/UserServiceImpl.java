package com.zjq.logback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjq.logback.entity.User;
import com.zjq.logback.mapper.UserMapper;
import com.zjq.logback.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {




}
