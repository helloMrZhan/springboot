package com.zjq.project.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试接口
 * @author zjq
 */
@RestController
public class HelloController {

    @RequestMapping("/hello/user")
    public String helloUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-user  "+name;
    }


    @RequestMapping("/hello/admin")
    public String helloAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-admin  "+name;
    }

}
