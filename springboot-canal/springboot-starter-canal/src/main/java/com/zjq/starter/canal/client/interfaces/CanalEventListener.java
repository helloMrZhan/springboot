package com.zjq.starter.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * canal 的事件接口层（表数据改变）
 *
 * @author
 * @created 2020/5/28 16:37
 * @Modified_By 阿导 2020/5/28 16:37
 */
@FunctionalInterface
public interface CanalEventListener {


    /**
     * 处理事件
     *
     * @param destination 指令
     * @param schemaName  库实例
     * @param tableName   表名
     * @param rowChange   詳細參數
     * @return
     * @time 2020/5/28 16:37
     */
    void onEvent(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange);

}
