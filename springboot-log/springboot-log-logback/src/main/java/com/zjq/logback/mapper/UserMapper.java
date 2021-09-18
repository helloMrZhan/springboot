package com.zjq.logback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjq.logback.entity.User;
import org.springframework.stereotype.Component;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Component
public interface UserMapper extends BaseMapper<User> {

}
