package com.zjq.mybatis.controller;

import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/masterList")
    public List<User> masterList() {

        List<User> list = userService.getMasterAllUser();
        return list;
    }

    @GetMapping("/slaveList")
    public List<User> slaveList() {

        List<User> list = userService.getSlaveAllUser();
        return list;
    }

}
