package com.zjq.starter.canal.client.interfaces;

import com.alibaba.otter.canal.client.CanalConnector;
import com.zjq.starter.canal.client.core.ListenerPoint;
import com.zjq.starter.canal.config.CanalConfig;

import java.util.List;
import java.util.Map;

/**
 * 信息转换工厂类接口层
 *
 * @author
 * @created 2020/5/28 14:33
 * @Modified_By 阿导 2020/5/28 14:33
 */
@FunctionalInterface
public interface TransponderFactory {

    /**
     * @param connector     canal 连接工具
     * @param config        canal 链接信息
     * @param listeners     实现接口的监听器
     * @param annoListeners 注解监听拦截
     * @return
     * @time 2020/5/28 14:43
     */
    MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalEventListener> listeners, List<ListenerPoint> annoListeners);
}
