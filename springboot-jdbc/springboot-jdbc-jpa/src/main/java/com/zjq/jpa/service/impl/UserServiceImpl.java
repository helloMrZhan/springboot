package com.zjq.jpa.service.impl;

import com.zjq.jpa.entity.User;
import com.zjq.jpa.mapper.UserMapper;
import com.zjq.jpa.service.UserService;
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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User add(User user) {
		return this.userMapper.save(user);
	}

	@Override
	public void update(User user) {
		this.userMapper.findById(user.getId()).ifPresent(
				user1 ->{
					userMapper.save(user);
				}
		);
	}

	@Override
	public List<User> list() {
		return this.userMapper.findAll();
	}

	@Override
	public void deleteById(Integer userId) {
		this.userMapper.deleteById(userId);
	}

	@Override
	public User getById(Integer userId) {
		return this.userMapper.findById(userId).get();
	}



}
