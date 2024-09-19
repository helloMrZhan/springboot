package com.zjq.rocketmq.service;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

/**
 * 生产者
 *
 * @author 共饮一杯无
 * @date 2024/09/19
 * @blog https://zhanjq.blog.csdn.net/ 
 */
@RequiredArgsConstructor
@Service
public class ProducerService {
  private final RocketMQTemplate rocketMQTemplate;

  /**
   * 发送消息
   *
   * @param message 消息体
   */
  public void send(String message) {
    /** 指定发送 zjq-topic */
    rocketMQTemplate.convertAndSend("zjq-topic", message);
  }
}