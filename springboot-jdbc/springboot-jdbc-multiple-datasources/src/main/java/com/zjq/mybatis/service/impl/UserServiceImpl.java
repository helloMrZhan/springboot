package com.zjq.mybatis.service.impl;

import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.mapper.master.MasterUserMapper;
import com.zjq.mybatis.mapper.slave.SlaveUserMapper;
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
public class UserServiceImpl implements UserService {

	@Autowired
	private MasterUserMapper masterUserMapper;

	@Autowired
	private SlaveUserMapper slaveUserMapper;

	@Override
	public int add(User user) {
		return this.masterUserMapper.add(user);
	}

	@Override
	public int update(User user) {
		return this.masterUserMapper.update(user);
	}

	@Override
	public List<User> list() {
		return this.masterUserMapper.list();
	}

	@Override
	public int deleteById(Long userId) {
		return this.masterUserMapper.deleteById(userId);
	}

	@Override
	public User getById(Long userId) {
		return this.masterUserMapper.getById(userId);
	}

	@Override
	public List<User> getMasterAllUser() {
		return masterUserMapper.getAllUser();
	}

	@Override
	public List<User> getSlaveAllUser() {
		return slaveUserMapper.getAllUser();
	}


}
