package com.zjq.starter.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 重命名表名稱操作
 *
 * @author
 * @CopyRight 青团社
 * @created 2020年05月30日 16:55:00
 * @Modified_By 阿导 2020/5/30 16:55
 */
public abstract class AbstractRenameTableOption extends AbstractDBOption {

    /**
     * 重命名表操作
     *
     * @created 2020/5/29 09:21
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.RENAME;
    }
}
