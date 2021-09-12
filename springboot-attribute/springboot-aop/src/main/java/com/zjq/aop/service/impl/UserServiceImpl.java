package com.zjq.aop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjq.aop.entity.User;
import com.zjq.aop.mapper.UserMapper;
import com.zjq.aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


}
