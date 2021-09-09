package com.zjq.mybatis.mapper;

import com.zjq.mybatis.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Component
@Mapper
public interface UserMapper {

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@Insert("insert into user(username,phone,age) values(#{username},#{phone},#{age})")
	int add(User user);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	@Update("update user set username=#{username},phone=#{phone},age=#{age} where id =#{id}")
    int update(User user);


	/**
	 * 查询用户列表
	 * @return
	 */
	@Select("select * from user")
	@Results(id = "User",value= {
			@Result(property = "username", column = "username", javaType = String.class),
			@Result(property = "phone", column = "phone", javaType = String.class),
			@Result(property = "age", column = "age", javaType = String.class)
	})
	List<User> list();

	/**
	 * 通过id删除用户
	 * @param userId
	 * @return
	 */
	@Delete("delete from user where id=#{userId}")
	int deleteById(Long userId);

	/**
	 * 通过id查询用户
	 * @param userId
	 * @return
	 */
	@Select("select * from user where id=#{userId}")
	@Results(id = "User",value= {
			@Result(property = "username", column = "username", javaType = String.class),
			@Result(property = "phone", column = "phone", javaType = String.class),
			@Result(property = "age", column = "age", javaType = String.class)
	})
	User getById(Long userId);
}
