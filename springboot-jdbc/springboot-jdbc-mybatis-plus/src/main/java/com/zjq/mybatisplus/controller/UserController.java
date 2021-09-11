package com.zjq.mybatisplus.controller;

import com.zjq.mybatisplus.entity.User;
import com.zjq.mybatisplus.service.UserService;
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

    @GetMapping("/list")
    public List<User> list() {

        List<User> list = userService.list();
        return list;
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId) {

        return userService.getById(userId);
    }

    @PostMapping
    public void add(@Validated @RequestBody User user) {
        userService.save(user);
    }

    @PutMapping
    public void update(@Validated @RequestBody User user) {
        userService.updateById(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {

        userService.removeById(userId);
    }

}
