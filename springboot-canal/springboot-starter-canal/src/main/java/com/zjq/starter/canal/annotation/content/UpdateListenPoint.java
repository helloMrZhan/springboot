package com.zjq.starter.canal.annotation.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.annotation.ListenPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 更新操作监听器
 *
 * @author
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ListenPoint(eventType = CanalEntry.EventType.UPDATE)
public @interface UpdateListenPoint {

    /**
     * canal 指令
     */
    @AliasFor(annotation = ListenPoint.class)
    String destination() default "";

    /**
     * 数据库实例
     */
    @AliasFor(annotation = ListenPoint.class)
    String[] schema() default {};

    /**
     * 监听的表
     */
    @AliasFor(annotation = ListenPoint.class)
    String[] table() default {};

}
