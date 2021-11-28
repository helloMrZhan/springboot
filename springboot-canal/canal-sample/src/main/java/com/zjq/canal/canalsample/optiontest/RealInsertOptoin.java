package com.zjq.canal.canalsample.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.canal.canalsample.mapper.Mapper;
import com.zjq.starter.canal.client.abstracts.option.content.AbstractInsertOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 需要自己实现的新增处理机制
 *
 * @author
 *
 * @created 2020年05月29日 09:47:00
 * @Modified_By 阿导 2020/5/29 09:47
 */
@Component
public class RealInsertOptoin extends AbstractInsertOption {

	@Autowired
	private Mapper mapper;


	/**
	 * 新增数据操作
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
		System.out.println("======================接口方式（新增数据操作）==========================");
		List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
		for (CanalEntry.RowData rowData : rowDatasList) {

			String sql = "use " + schemaName + ";\n";
			StringBuffer colums = new StringBuffer();
			StringBuffer values = new StringBuffer();
			rowData.getAfterColumnsList().forEach((c) -> {
				colums.append(c.getName() + ",");
				values.append("'" + c.getValue() + "',");
			});


			sql += "INSERT INTO " + tableName + "(" + colums.substring(0, colums.length() - 1) + ") VALUES(" + values.substring(0, values.length() - 1) + ");";
			System.out.println(sql);
			//mapper.doOption(sql);

		}
		System.out.println("\n======================================================");

	}
}
