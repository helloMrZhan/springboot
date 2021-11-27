package com.zjq.starter.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 数据库操作接口层
 *
 * @author
 * @created 2020年05月28日 21:21:00
 * @Modified_By 阿导 2020/5/28 21:21
 */
@FunctionalInterface
public interface IDBOption {

    /**
     * 操作
     *
     * @param destination 指令
     * @param schemaName  实例名称
     * @param tableName   表名称
     * @param rowChange   数据
     * @return
     * @time 2020/5/29 08:59
     */
    void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange);
}
