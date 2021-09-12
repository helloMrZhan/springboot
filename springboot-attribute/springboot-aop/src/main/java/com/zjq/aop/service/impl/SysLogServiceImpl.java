package com.zjq.aop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjq.aop.entity.SysOperationLog;
import com.zjq.aop.entity.User;
import com.zjq.aop.mapper.SysLogMapper;
import com.zjq.aop.mapper.UserMapper;
import com.zjq.aop.service.SysLogService;
import com.zjq.aop.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysOperationLog> implements SysLogService {


}
