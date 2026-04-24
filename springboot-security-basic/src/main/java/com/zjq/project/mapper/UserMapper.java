package com.zjq.project.mapper;

import com.zjq.project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zjq
 * @date 2023/9/1
 */
@Mapper
public interface UserMapper {

    @Select("select * from sys_user where username = #{username}")
    public User findByUsername(String username);
}