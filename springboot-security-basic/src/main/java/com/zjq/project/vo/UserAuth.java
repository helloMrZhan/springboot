package com.zjq.project.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjq
 * @date 2023/9/1
 */
@Data
public class UserAuth implements UserDetails {

    private String username; //固定不可更改
    private String password;//固定不可更改
    private String nickName;  //扩展属性  昵称
    private List<String> roles; //角色列表


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles==null) {return null;}
        //把角色类型转换并放入对应的集合
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}