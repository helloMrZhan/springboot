package com.zjq.mybatis.service.impl;

import com.google.common.collect.Sets;
import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.mapper.UserMapper;
import com.zjq.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
	public int add(User user) {
		return this.userMapper.add(user);
	}

	@Override
	public int update(User user) {
		return this.userMapper.update(user);
	}

	@Override
	public List<User> list() {
		return this.userMapper.list();
	}

	@Override
	public int deleteById(Long userId) {
		return this.userMapper.deleteById(userId);
	}

	@Override
	public User getById(Long userId) {
		return this.userMapper.getById(userId);
	}

	public static void main(String[] args) {
		boolean contains1 = Sets.newHashSet("02", "03").contains("02");
		System.out.println(contains1);
		HashSet<Object> set = new HashSet<>();
		set.add("02");
		set.add("03");
		boolean contains = set.contains("02");
		System.out.println(contains);
	}

}
