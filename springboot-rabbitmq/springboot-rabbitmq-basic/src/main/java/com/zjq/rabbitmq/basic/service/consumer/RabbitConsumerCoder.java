package com.zjq.rabbitmq.basic.service.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * @author zjq
 */
public class RabbitConsumerCoder {
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

        // --- 代码清单3-9 关键消费代码
        // 5. 设置消费者最大未确认消息数（防止内存溢出）
        channel.basicQos(64);
        boolean autoAck = false;

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
        channel.basicConsume(QUEUE_NAME, autoAck,"consumerTag",consumer);

        // 取消一个消费者的订阅
//        channel.basicCancel("consumerTag");

        // 代码清单3-10 拉模式获取一个消息
//        GetResponse getResponse = channel.basicGet(QUEUE_NAME, autoAck);
//        if (getResponse != null) {
//            System.out.println("✅ 获取消息成功: " + new String(getResponse.getBody()));
//        }
//        channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);

        // 8. 等待一段时间后关闭资源（实际项目中通常不会主动关闭）
        TimeUnit.SECONDS.sleep(5);

        // 代码清单3-11 监听关闭
        connection.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                System.out.println("❌ 连接关闭");
            }
        });

        // 代码清单3-12 监听硬关闭
        connection.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                if (cause.isHardError()) {
                    Connection conn = (Connection) cause.getReference();
                    if (!cause.isInitiatedByApplication()) {
                        Method reason = cause.getReason();
                        // 处理非应用主动触发的硬错误
                        // ...
                    }
                    // 其他逻辑...
                } else {
                    Channel ch = (Channel) cause.getReference();
                    // 处理软关闭（如正常关闭、超时等）
                    // ...
                }
            }
        });


        // 9. 关闭通道和连接
        channel.close();
        connection.close();


    }
}