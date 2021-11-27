package com.zjq.starter.canal.client.core;

/**
 * Canal 的一些信息
 *
 * @created 2020年05月28日 18:39:00
 * @Modified_By 阿导 2020/5/28 18:39
 */
public class CanalMsg {

    /**
     * 指令
     */
    private String destination;
    /**
     * 数据库实例名称
     */
    private String schemaName;
    /**
     * 数据库表名称
     */
    private String tableName;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
