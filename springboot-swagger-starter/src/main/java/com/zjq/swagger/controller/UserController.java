package com.zjq.swagger.controller;

import com.zjq.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(value = "用户管理", description = "系统中的用户操作")
public class UserController {

    /**
     * 初始化一个用户集合
     */
    private List<User> userList = Arrays.asList(
            new User(1, "张三"),
            new User(2, "李四"),
            new User(3, "王五")
    );
    

    @PostMapping
    @ApiOperation(value = "创建用户", notes = "创建一个新的用户")
    public ResponseEntity<String> createUser(@RequestBody @ApiParam(value = "用户信息", required = true) User user) {
        userList.add(user);
        return ResponseEntity.ok("创建成功");
    }

    @GetMapping
    @ApiOperation(value = "获取所有用户", notes = "返回的所有用户列表")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取用户", notes = "根据提供的用户ID返回单一用户")
    public ResponseEntity<User> getUserById(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Integer id) {
        //通过用户ID获取用户
        User user = userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新用户", notes = "根据提供的用户ID更新用户信息")
    public ResponseEntity<String> updateUser(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Integer id,
                                           @RequestBody @ApiParam(value = "用户信息", required = true) User user) {
        //通过用户ID修改用户
        userList.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(u -> {
            u.setUsername(user.getUsername());
        });
        return ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "根据提供的用户ID删除用户")
    public ResponseEntity<String> deleteUser(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Integer id) {
        //通过用户ID删除用户
        userList.removeIf(u -> u.getId().equals(id));
        return ResponseEntity.ok("删除成功");
    }

    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", paramType = "query",dataType="int",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", paramType = "query",dataType="int",defaultValue = "10"),
    })
    @ApiResponses({
            @ApiResponse(code=10001,message="xx业务规则不符合")
    })
    @ApiOperation(value = "获取分页查询用户", notes = "分页查询用户列表")
    public ResponseEntity<List<User>> getPageUsers(Integer page,Integer pageSize) {
        return ResponseEntity.ok(userList);
    }
}