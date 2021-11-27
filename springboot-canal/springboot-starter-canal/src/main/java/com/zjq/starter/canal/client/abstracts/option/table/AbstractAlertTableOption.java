package com.zjq.starter.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 修改表操作
 *
 * @author
 * @created 2020年05月29日 13:42:00
 * @Modified_By 阿导 2020/5/29 13:42
 */
public abstract class AbstractAlertTableOption extends AbstractDBOption {
    /**
     * 修改表操作
     *
     * @created 2020/5/29 09:21
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.ALTER;
    }
}
