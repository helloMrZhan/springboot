package com.zjq.tkmybatis.mapper;

import com.zjq.tkmybatis.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Component
public interface UserMapper extends Mapper<User>,MySqlMapper<User> {

}
