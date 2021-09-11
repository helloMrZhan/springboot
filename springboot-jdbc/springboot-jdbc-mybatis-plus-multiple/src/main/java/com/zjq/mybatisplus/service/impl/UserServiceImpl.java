package com.zjq.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjq.mybatisplus.entity.User;
import com.zjq.mybatisplus.mapper.UserMapper;
import com.zjq.mybatisplus.service.UserService;
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


    @Override
    public List<User> getMasterAllUser() {
        return this.list();
    }
}
