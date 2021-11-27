package com.zjq.starter.canal.annotation.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.zjq.starter.canal.annotation.ListenPoint;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 刪除索引操作
 *
 *
 * @CopyRight 青团社
 * @created 2020年05月30日 17:12:00
 * @Modified_By 阿导 2020/5/30 17:12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ListenPoint(eventType = CanalEntry.EventType.DINDEX)
public @interface DropIndexListenPoint {

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
