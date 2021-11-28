package com.zjq.canal.canalsample.test;

import com.zjq.starter.canal.client.abstracts.option.content.AbstractInsertOption;
import com.zjq.starter.canal.client.abstracts.option.content.AbstractDeleteOption;
import com.zjq.starter.canal.client.abstracts.option.content.AbstractInsertOption;
import com.zjq.starter.canal.client.abstracts.option.content.AbstractUpdateOption;
import com.zjq.starter.canal.client.core.DealCanalEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 实现接口方式测试
 *
 * @author
 *
 * @created 2020/5/28 17:31
 * @Modified_By 阿导 2020/5/28 17:31
 */
@Component
public class MyEventListenerimpl extends DealCanalEventListener {

    @Autowired
    public MyEventListenerimpl(@Qualifier("realInsertOptoin") AbstractInsertOption insertOption,
							   @Qualifier("realDeleteOption") AbstractDeleteOption deleteOption,
							   @Qualifier("realUpdateOption") AbstractUpdateOption updateOption) {
        super(insertOption, deleteOption, updateOption);
    }

}
