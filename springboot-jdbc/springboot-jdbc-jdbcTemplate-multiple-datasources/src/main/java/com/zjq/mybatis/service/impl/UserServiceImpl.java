package com.zjq.mybatis.service.impl;

import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
	@Qualifier("masterJdbcTemplate")
	private JdbcTemplate masterJdbcTemplate;

	@Autowired
	@Qualifier("slaveJdbcTemplate")
	private JdbcTemplate slaveJdbcTemplate;


	@Override
	public List<User> getMasterAllUser() {
		return masterJdbcTemplate.query("select * from user",
				new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public List<User> getSlaveAllUser() {
		return slaveJdbcTemplate.query("select * from user",
				new BeanPropertyRowMapper<>(User.class));
	}


}
