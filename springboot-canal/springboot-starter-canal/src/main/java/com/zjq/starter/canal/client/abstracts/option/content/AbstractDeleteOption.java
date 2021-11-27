package com.zjq.starter.canal.client.abstracts.option.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 删除数据
 *
 * @author
 * @created 2020年05月29日 09:02:00
 * @Modified_By 阿导 2020/5/29 09:02
 */

public abstract class AbstractDeleteOption extends AbstractDBOption {

    /**
     * 设置删除操作
     *
     * @param
     * @return
     * @time 2020/5/29 09:22
     */
    @Override
    protected void setEventType() {
        this.eventType = CanalEntry.EventType.DELETE;
    }

}
