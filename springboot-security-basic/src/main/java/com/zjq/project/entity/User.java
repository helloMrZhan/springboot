package com.zjq.project.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    public Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * open_id标识
     */
    private String openId;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型（0:系统用户,1:客户）
     */
    private String userType;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 数据状态（0正常 1停用）
     */
    private String dataState;

    /**
     * 创建时间
     */
    public LocalDateTime createTime;

    /**
     * 更新时间
     */
    public LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 备注
     */
    private String remark;

}