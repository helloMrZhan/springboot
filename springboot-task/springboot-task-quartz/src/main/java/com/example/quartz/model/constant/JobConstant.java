package com.example.quartz.model.constant;

/**
 * @author zjq
 * @Description:
 **/
public interface JobConstant {
    /**
     * 手动
     */
    Integer MANUAL = 1;
    /**
     * 自动
     */
    Integer AUTO = 2;

    /**
     * 系统自动执行
     */
    String SYSTEM_RUN = "sys";

    /**
     * 用户手动触发
     */
    String USER_RUN = "user";

    /**
     * 启用
     */
    Integer ENABLE = 0;
    /**
     * 禁用
     */
    Integer DISABLE = 1;
    /**
     * 执行
     */
    Integer EXECUTE = 1;
    /**
     * 暂停
     */
    Integer PAUSE = 2;

}
