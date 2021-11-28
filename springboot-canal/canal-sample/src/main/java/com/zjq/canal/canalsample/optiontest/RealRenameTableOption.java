package com.zjq.canal.canalsample.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.table.AbstractRenameTableOption;
import org.springframework.stereotype.Component;

/**
 * 真正的重新命名操作
 *
 * @author
 * @CopyRight 青团社
 * @created 2020年05月30日 17:04:00
 * @Modified_By 阿导 2020/5/30 17:04
 */
@Component
public class RealRenameTableOption extends AbstractRenameTableOption {


	/**
	 * 重命名表操作
	 *
	 * @param destination 指令
	 * @param schemaName  实例名称
	 * @param tableName   表名称
	 * @param rowChange   数据
	 * @return
	 * @author
	 * @time 2020/5/29 08:59
	 *
	 */
	@Override
	public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
		System.out.println("======================接口方式（重新命名表操作）==========================");
		System.out.println("use "+schemaName+";\n"+rowChange.getSql());
		System.out.println("\n======================================================");
	}
}
