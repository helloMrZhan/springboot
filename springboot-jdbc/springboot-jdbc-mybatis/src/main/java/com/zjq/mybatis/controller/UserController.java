package com.zjq.mybatis.controller;

import com.zjq.mybatis.common.R;
import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("userXMLServiceImpl")
    private UserService userService;

    @GetMapping("/list")
    public R list() {

        List<User> list = userService.list();
        return R.ok(list);
    }

    @GetMapping("/{userId}")
    public R getById(@PathVariable Long userId) {

        return R.ok(userService.getById(userId));
    }

    @PostMapping
    public R add(@Validated @RequestBody User user) {
        userService.add(user);
        return R.ok("新增成功");
    }

    @PutMapping
    public R update(@Validated @RequestBody User user) {
        userService.update(user);
        return R.ok("更新成功");
    }

    @DeleteMapping("/{userId}")
    public R delete(@PathVariable Long userId) {

        userService.deleteById(userId);
        return R.ok("删除成功");
    }

    @GetMapping("/findAllUserAndOrders")
    public R findAllUserAndOrders() {

        return R.ok(userService.findAllUserAndOrders());
    }
}
