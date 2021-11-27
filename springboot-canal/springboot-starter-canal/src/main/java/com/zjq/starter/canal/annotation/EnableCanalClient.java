package com.zjq.starter.canal.annotation;

import com.zjq.starter.canal.config.CanalClientConfiguration;
import com.zjq.starter.canal.config.CanalConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 Canal 客户端
 *
 * @author
 * @created 2020/5/28 14:08
 * @Modified_By 阿导 2020/5/28 14:08
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CanalConfig.class, CanalClientConfiguration.class})
public @interface EnableCanalClient {
}
