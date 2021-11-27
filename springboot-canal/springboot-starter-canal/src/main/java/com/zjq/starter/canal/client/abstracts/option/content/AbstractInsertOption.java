package com.zjq.starter.canal.client.abstracts.option.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.AbstractDBOption;

/**
 * 新增数据
 *
 *
 *
 * @author
 * @created 2020年05月29日 08:58:00
 * @Modified_By 阿导 2020/5/29 08:58
 */

public abstract class AbstractInsertOption extends AbstractDBOption {


	/**
	 * 设置新增操作
	 *
	 * @param
	 * @return
	 *
	 * @time 2020/5/29 09:24
	 *
	 */
	@Override
	protected void setEventType() {
		this.eventType = CanalEntry.EventType.INSERT;
	}
}
