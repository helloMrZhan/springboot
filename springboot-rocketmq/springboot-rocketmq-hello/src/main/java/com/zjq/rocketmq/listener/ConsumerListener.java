package com.zjq.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 消费者监听器
 *
 * @author 共饮一杯无
 */
@Slf4j
@RocketMQMessageListener(topic = "zjq-topic", consumerGroup = "zjq-consumer-group")
@Component
public class ConsumerListener implements RocketMQListener<String> {

  @Override
  public void onMessage(String s) {
    log.info("接受到【zjq-topic】信息：{}", s);
  }
}
