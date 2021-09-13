package com.zjq.tkmybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjq.tkmybatis.entity.User;
import com.zjq.tkmybatis.mapper.UserMapper;
import com.zjq.tkmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
		return this.userMapper.insert(user);
	}

	@Override
	public int update(User user) {
		return this.userMapper.updateByPrimaryKey(user);
	}

	@Override
	public List<User> list() {
		return this.userMapper.selectAll();
	}

	@Override
	public int deleteById(Long userId) {
		return this.userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public User getById(Long userId) {
		return this.userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public PageInfo page() {
		Example example = new Example(User.class);
		// 过滤
		example.createCriteria().orEqualTo("phone", "15656455662").andLike("username", "%666%");
		// 排序
		example.setOrderByClause("id desc");
		int count = userMapper.selectCountByExample(example);
		// 分页
		PageHelper.startPage(1, 3);
		// 查询
		List<User> userList = userMapper.selectByExample(example);
		PageInfo<User> userPageInfo = new PageInfo<>(userList);
		return userPageInfo;
	}


}
