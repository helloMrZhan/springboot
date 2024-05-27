package com.zjq.mybatis.service.impl;

import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.mapper.UserXMLMapper;
import com.zjq.mybatis.service.UserService;
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
public class UserXMLServiceImpl implements UserService {

    @Autowired
    private UserXMLMapper userXMLMapper;

    @Override
    public int add(User user) {
        return this.userXMLMapper.add(user);
    }

    @Override
    public int update(User user) {
        return this.userXMLMapper.update(user);
    }

    @Override
    public List<User> list() {
        return this.userXMLMapper.list();
    }

    @Override
    public int deleteById(Long userId) {
        return this.userXMLMapper.deleteById(userId);
    }

    @Override
    public User getById(Long userId) {
        return this.userXMLMapper.getById(userId);
    }

    @Override
    public List<User> findAllUserAndOrders() {
        return  this.userXMLMapper.findAllUserAndOrders();
    }


}
