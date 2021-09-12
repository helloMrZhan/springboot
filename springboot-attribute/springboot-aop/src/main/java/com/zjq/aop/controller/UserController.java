package com.zjq.aop.controller;

import com.zjq.aop.anno.OperationLog;
import com.zjq.aop.entity.User;
import com.zjq.aop.service.UserService;
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

    @OperationLog(title = "查找用戶列表")
    @GetMapping("/list")
    public List<User> list() {

        List<User> list = userService.list();
        return list;
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId) {

        return userService.getById(userId);
    }

    @OperationLog(title = "新增用戶")
    @PostMapping
    public void add(@Validated @RequestBody User user) {
        userService.save(user);
    }

    @OperationLog(title = "更新用戶")
    @PutMapping
    public void update(@Validated @RequestBody User user) {
        userService.updateById(user);
    }

    @OperationLog(title = "删除用戶")
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {

        userService.removeById(userId);
    }

}
