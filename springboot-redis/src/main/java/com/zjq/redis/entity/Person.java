package com.zjq.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zjq
 * @Description:
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person{

    private Integer id;

    private String name;

    private Integer age;

}
