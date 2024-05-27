package com.zjq.mybatis.controller;

import com.zjq.mybatis.common.R;
import com.zjq.mybatis.entity.User;
import com.zjq.mybatis.service.OrderService;
import com.zjq.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author  共饮一杯无
 * @date 2024/5/25 22:08
 * @description: 订单控制层
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public R getUserByOrderId(@PathVariable Long orderId) {

        return R.ok(orderService.getUserByOrderId(orderId));
    }

}
