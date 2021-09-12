package com.zjq.aop.anno;

import com.zjq.aop.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * <p>自定义操作日志注解</p>
 *
 * @author zjq
 * @date 2021/06/17
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 日志标题 为空的话取@ApiOperation的Value值
     */
    String title() default "";

    /**
     * 日志类型
     * @return
     */
    LogTypeEnum logType() default LogTypeEnum.OTHER;
}
