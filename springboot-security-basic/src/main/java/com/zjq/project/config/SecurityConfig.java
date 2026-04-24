package com.zjq.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zjq
 * @date 2023/10/19
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin()  //自定义登录页面
                .loginPage("/login.html") //指定登录页面
                .loginProcessingUrl("/login") //放行请求路径
                .permitAll() //登录页和登录访问路径无需登录也可以访问
                .and()
                .authorizeRequests()
                .antMatchers("/css/**","/images/**").permitAll() //放行css和images
                .antMatchers("/hello/user").hasRole("USER") //放行css和images
                .antMatchers("/hello/admin").hasRole("ADMIN") //放行css和images
                .anyRequest().authenticated()//其他的任何请求都要经过验证
                .and()
                .csrf().disable();//关闭csrf防护
        //返回
        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$yi6zIXQ4EW28DrupAUiznOZWl34VeMdaq2u5bnyyEVjf6EQsfIzHy")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$EPRURztGD71QH.0VbvTpx.IqEuEJFdKfN2BkcL9WVnimn/KLcFt4G")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/
}
