package com.zjq.springcache.controller;

import com.zjq.springcache.entity.User;
import com.zjq.springcache.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @author 共饮一杯无
 * @date 2025/6/22 15:29
 * @description: 用户控制器，提供增删查改操作并集成缓存与Knife4j文档
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserController｜用户管理接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建用户并缓存数据
     */
    @PostMapping
    @CachePut(value = "userCache", key = "#user.id")
    @ApiOperation("创建用户接口")
    public User save(@RequestBody @ApiParam(name = "用户实体") User user) {
        userMapper.add(user);
        return user;
    }

    /**
     * 根据ID获取用户信息，优先从缓存读取
     */
    @GetMapping
    @Cacheable(cacheNames = "userCache", key = "#id")
    @ApiOperation("根据ID查询用户")
    public User getById(@ApiParam(name = "用户ID") @RequestParam Long id) {
        return userMapper.getById(id);
    }

    /**
     * 删除指定ID的用户及其缓存
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "userCache", key = "#id")
    @ApiOperation("删除用户接口")
    public void deleteById(@ApiParam(name = "用户ID") @RequestParam Long id) {
        userMapper.deleteById(id);
    }

    /**
     * 清空所有用户缓存
     */
    @DeleteMapping("/delAll")
    @CacheEvict(cacheNames = "userCache", allEntries = true)
    @ApiOperation("清空用户缓存接口")
    public void deleteAll() {
        userMapper.deleteAll();
    }
}
