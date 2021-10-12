package com.zjq.upload.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zjq
 * @date 2021/9/29 21:50
 * <p>title:base PO</p>
 * <p>description:</p>
 */
@Data
public class BasePo implements Serializable {
    private static final long serialVersionUID = 0x20161228;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date")
    private Date updatedDate;
}
