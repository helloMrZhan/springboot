package com.zjq.aop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>用户实体</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String phone;

    private Integer age;
}
