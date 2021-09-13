package com.zjq.tkmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>用户实体</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String username;

    private String phone;

    private Integer age;
}
