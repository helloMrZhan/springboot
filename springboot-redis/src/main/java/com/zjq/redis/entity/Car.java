package com.zjq.redis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zjq
 * @date 2020/3/6 22:11
 * <p>title:</p>
 * <p>company:zjhcsoft</p>
 * <p>description:</p>
 */
@Data
public class Car implements Serializable{

    private Integer id;

    private String name;

    private String color;
}
