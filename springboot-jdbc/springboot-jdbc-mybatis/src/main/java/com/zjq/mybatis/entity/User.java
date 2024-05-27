package com.zjq.mybatis.entity;


import lombok.Data;

import java.util.List;


/**
 * <p>用户实体</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Data
public class User {

    /**
     * 用户的唯一标识符
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码。请注意，这应该在实际应用中进行加密存储。
     */
    private String password;

    /**
     * 电话号码。
     */
    private String phone;

    /**
     * 年龄。
     */
    private Integer age;

    /**
     * 代表当前用户具备哪些订单
     */
    private List<Order> orderList;
}
