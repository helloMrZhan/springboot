package com.zjq.starter.canal.annotation;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.lang.annotation.*;

/**
 * 监听数据库的操作
 *
 * @author
 * @created 2020/5/28 15:39
 * @Modified_By 阿导 2020/5/28 15:39
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListenPoint {

    /**
     * canal 指令
     */
    String destination() default "";

    /**
     * 数据库实例
     */
    String[] schema() default {};

    /**
     * 监听的表
     */
    String[] table() default {};

    /**
     * 监听操作的类型
     */
    CanalEntry.EventType[] eventType() default {};

}
