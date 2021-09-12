package com.zjq.aop.enums;

import lombok.RequiredArgsConstructor;

/**
 * <p>日志类型</p>
 *
 * @author zjq
 * @date 2021/06/27
 */
@RequiredArgsConstructor
public enum LogTypeEnum {

    /**
     * 其它
     */
    OTHER(1,"其他"),

    /**
     * 新增
     */
    INSERT(2,"新增"),

    /**
     * 修改
     */
    UPDATE(3,"修改"),

    /**
     * 删除
     */
    DELETE(4,"删除"),

    /**
     * 授权
     */
    GRANT(5,"授权"),

    /**
     * 复制
     */
    COPY(6,"复制"),

    /**
     * 恢复
     */
    RECOVERY(7,"恢复"),

    /**
     * 错误日志类型
     */
    ERROR(99, "错误日志");

    /**
     * 类型
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
