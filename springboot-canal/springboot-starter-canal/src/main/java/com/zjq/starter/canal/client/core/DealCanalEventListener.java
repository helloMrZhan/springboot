package com.zjq.starter.canal.client.core;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;
import com.zjq.starter.canal.client.interfaces.CanalEventListener;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 处理 Canal 监听器
 *
 * @created 2020年05月28日 20:13:00
 * @Modified_By 阿导 2020/5/28 20:13
 */
@SuppressWarnings("all")
public class DealCanalEventListener implements CanalEventListener {

    /**
     * 頭結點
     */
    private AbstractDBOption header;

    /**
     * 默認構造方法，必須傳入鏈路
     *
     * @param dbOptions
     * @return
     * @time 2020/5/30 17:46
     * @CopyRight 杭州弧途科技有限公司（青团社）
     */
    public DealCanalEventListener(AbstractDBOption... dbOptions) {
        AbstractDBOption tmp = null;
        for (AbstractDBOption dbOption : dbOptions) {
            if (tmp != null) {
                tmp.setNext(dbOption);
            } else {
                this.header = dbOption;
            }
            tmp = dbOption;
        }

    }

    public DealCanalEventListener(List<AbstractDBOption> dbOptions) {
        if (CollectionUtils.isEmpty(dbOptions)) {
            return;
        }
        AbstractDBOption tmp = null;
        for (AbstractDBOption dbOption : dbOptions) {
            if (tmp != null) {
                tmp.setNext(dbOption);
            } else {
                this.header = dbOption;
            }
            tmp = dbOption;
        }
    }

    /**
     * 处理数据库的操作
     *
     * @param destination
     * @param schemaName
     * @param tableName
     * @param rowChange
     * @return
     * @time 2020/5/29 09:43
     */
    @Override
    public void onEvent(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
        this.header.doChain(destination, schemaName, tableName, rowChange);
    }


}
