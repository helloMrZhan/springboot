package com.zjq.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * security入门案例
 * @author 共饮一杯无
 */
@RestController
@RequestMapping("/security")
public class HelloSecurityController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello security";
    }
}