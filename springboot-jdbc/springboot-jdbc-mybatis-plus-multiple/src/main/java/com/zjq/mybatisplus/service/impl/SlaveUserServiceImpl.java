package com.zjq.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjq.mybatisplus.entity.User;
import com.zjq.mybatisplus.mapper.UserMapper;
import com.zjq.mybatisplus.service.SlaveUserService;
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
@DS("slave")
public class SlaveUserServiceImpl extends ServiceImpl<UserMapper,User> implements SlaveUserService {


    @Override
    public List<User> getSlaveAllUser() {

        return this.list();
    }
}
