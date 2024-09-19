package com.zjq.rocketmq.controller;

import com.zjq.rocketmq.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产者
 *
 * @author 共饮一杯无
 * @date 2024/09/19
 * @blog https://zhanjq.blog.csdn.net/
 */
@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerController {

  private final ProducerService producerService;

  @GetMapping("/send")
  public String send(String msg) {
    producerService.send(msg);

    return "success";
  }
}