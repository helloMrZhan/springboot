package com.zjq.rabbitmq.basic.service.consumer;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * @author zjq
 */
public class RabbitConsumer {
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "192.168.253.183";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1. 创建服务器地址
        Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};

        // 2. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("KfOXpGSwU7iK");

        // 3. 建立连接
        Connection connection = factory.newConnection(addresses);

        // 4. 创建信道（Channel）
        Channel channel = connection.createChannel();

        // 5. 设置消费者最大未确认消息数（防止内存溢出）
        channel.basicQos(64);

        // 6. 定义消费者回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                      AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    // 打印接收到的消息内容
                    System.out.println("✅ 消息已接收: " + new String(body));

                    // 模拟处理耗时（比如业务逻辑）
                    TimeUnit.SECONDS.sleep(1);

                    // 确认消息已消费（手动 ACK）
                    channel.basicAck(envelope.getDeliveryTag(), false);

                } catch (Exception e) {
                    // 处理失败，可以选择：
                    // 1. 重新入队
                    // channel.basicNack(envelope.getDeliveryTag(), false, true);
                    // 2. 拒绝并丢弃或进入死信队列
                    channel.basicNack(envelope.getDeliveryTag(), false, false);
                }
            }
        };


        // 7. 开始消费消息（非阻塞，等待消息到达）
        channel.basicConsume(QUEUE_NAME, consumer);

        // 8. 等待一段时间后关闭资源（实际项目中通常不会主动关闭）
        TimeUnit.SECONDS.sleep(5);

        // 9. 关闭通道和连接
        channel.close();
        connection.close();
    }
}