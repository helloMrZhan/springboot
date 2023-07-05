package com.zjq.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security配置类
 * @author 共饮一杯无
 */
//@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * http请求处理方法
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.httpBasic()//开启httpbasic认证
        .and().authorizeRequests().
        anyRequest().authenticated();//所有请求都需要登录认证才能访问*/
        http.formLogin()//开启表单认证
                .and().authorizeRequests()
                .anyRequest().authenticated();//所有请求都需要登录认证才能访问;
    }
}