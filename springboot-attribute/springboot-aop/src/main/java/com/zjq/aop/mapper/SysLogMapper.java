package com.zjq.aop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjq.aop.entity.SysOperationLog;
import com.zjq.aop.entity.User;
import org.springframework.stereotype.Component;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Component
public interface SysLogMapper extends BaseMapper<SysOperationLog> {

}
