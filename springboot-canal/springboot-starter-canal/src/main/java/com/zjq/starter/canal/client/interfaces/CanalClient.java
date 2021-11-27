package com.zjq.starter.canal.client.interfaces;

/**
 * canal 客户端接口层
 *
 * @author
 * @created 2020/5/28 14:10
 * @Modified_By 阿导 2020/5/28 14:10
 */


public interface CanalClient {

    /**
     * 开启 canal 客户端，并根绝配置连接到 canal ,然后进行针对性的监听
     *
     * @param
     * @return
     * @time 2020/5/28 14:10
     */
    void start();


    /**
     * 关闭 canal 客户端
     *
     * @param
     * @return
     * @time 2020/5/28 14:12
     */
    void stop();

    /**
     * 判断 canal 客户端是否是开启状态
     *
     * @param
     * @return
     * @time 2020/5/28 14:12
     */
    boolean isRunning();
}
