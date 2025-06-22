package com.zjq.poi.entity;

import lombok.Data;

/**
 * 表头的实体类： 在具体的项目里，可以是你从数据库里查询出来的数据
 * @author zjq
 */
@Data
public class TitleEntity {
    public  String t_id;
    public  String t_pid;
    public  String t_content;
    public  String t_fielName;
    public TitleEntity(){}
    public TitleEntity(String t_id, String t_pid, String t_content, String t_fielName) {
        this.t_id = t_id;
        this.t_pid = t_pid;
        this.t_content = t_content;
        this.t_fielName = t_fielName;
    }
 
}