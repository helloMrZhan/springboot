package com.zjq.starter.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 創建索引操作
 *
 * @author
 * @CopyRight 青团社
 * @created 2020年05月30日 17:16:00
 * @Modified_By 阿导 2020/5/30 17:16
 */
public abstract class AbstractCreateIndexOption extends AbstractDBOption {
    /**
     * 創建索引操作
     *
     * @created 2020/5/29 09:21
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.CINDEX;
    }
}
