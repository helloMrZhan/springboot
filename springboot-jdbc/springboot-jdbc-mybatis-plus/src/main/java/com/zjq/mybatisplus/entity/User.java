package com.zjq.mybatisplus.entity;

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

    /**
     * 需要指定IdType.AUTO，不然会报如下错误：
     * nested exception is org.apache.ibatis.reflection.ReflectionException:
     * Could not set property 'id' of 'class com.zjq.mybatisplus.entity.User' with value '1437045027330686978' Cause: java.lang.IllegalArgumentException: argument type mismatc
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String phone;

    private Integer age;
}
