package com.zjq.redis.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author zjq
 * @date 2020/3/6 22:11
 * <p>title:</p>
 * <p>company:zjq</p>
 * <p>description:</p>
 */
@Data
@ToString
public class Car implements Serializable{

    private Integer id;

    private String name;

    private String color;
}
