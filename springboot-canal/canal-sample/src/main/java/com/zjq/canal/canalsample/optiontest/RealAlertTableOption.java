package com.zjq.canal.canalsample.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.client.abstracts.option.table.AbstractAlertTableOption;
import org.springframework.stereotype.Component;

/**
 * 真正的修改表信息操作
 *
 * @author
 * @created 2020年05月29日 15:02:00
 * @Modified_By 阿导 2020/5/29 15:02
 */
@Component
public class RealAlertTableOption extends AbstractAlertTableOption {

    /**
     * 修改表字段操作
     *
     * @param destination 指令
     * @param schemaName  实例名称
     * @param tableName   表名称
     * @param rowChange   数据
     * @return
     * @author
     * @time 2020/5/29 08:59
     */
    @Override
    public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
        System.out.println("======================接口方式（修改表信息操作）==========================");
        System.out.println("use " + schemaName + ";\n" + rowChange.getSql());
        System.out.println("\n======================================================");
    }
}
