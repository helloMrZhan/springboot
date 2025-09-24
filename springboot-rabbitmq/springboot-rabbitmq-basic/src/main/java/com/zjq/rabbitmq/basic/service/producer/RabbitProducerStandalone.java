package com.zjq.rabbitmq.basic.service.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.zjq.rabbitmq.basic.SpringbootRabbitMQApplication;
import com.zjq.rabbitmq.basic.config.RabbitMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitProducerStandalone {
    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    // 可提取为配置项，此处简化
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY   = "routingkey_demo";
    private static final String QUEUE_NAME    = "queue_demo";

    /**
     * 在 Bean 初始化后自动执行消息发送
     */
    @PostConstruct
    public void sendMessages() {

        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(rabbitMQProperties.getHost());
            factory.setPort(Integer.parseInt(rabbitMQProperties.getPort()));
            factory.setUsername(rabbitMQProperties.getUsername());
            factory.setPassword(rabbitMQProperties.getPassword());

            connection = factory.newConnection();
            channel = connection.createChannel();

            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 绑定
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // 发送消息
            String message = "Hello World!";
            channel.basicPublish(
                    EXCHANGE_NAME,
                    ROUTING_KEY,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("✅ 消息已发送: " + message);

        } catch (IOException | TimeoutException e) {
            System.err.println("❌ 发送消息失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResource(channel, connection);
        }
    }

    private static void closeResource(Channel channel, Connection connection) {
        if (channel != null && channel.isOpen()) {
            try { channel.close(); } catch (Exception e) { /* 忽略 */ }
        }
        if (connection != null) {
            try { connection.close(); } catch (Exception e) { /* 忽略 */ }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        // 启动 Spring 容器（最小化启动）
        ConfigurableApplicationContext context =
            SpringApplication.run(SpringbootRabbitMQApplication.class, args); // 注意：需创建一个最小配置类

        // 获取配置对象
        RabbitMQProperties properties = context.getBean(RabbitMQProperties.class);

        // 执行发送逻辑
        sendMessage(properties);

        // 可选：延迟关闭容器（如果需要异步消费等）
        Thread.sleep(2000);
        context.close();
    }

    private static void sendMessage(RabbitMQProperties rabbitMQProperties) {
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(rabbitMQProperties.getHost());
            factory.setPort(Integer.parseInt(rabbitMQProperties.getPort()));
            factory.setUsername(rabbitMQProperties.getUsername());
            factory.setPassword(rabbitMQProperties.getPassword());

            connection = factory.newConnection();
            channel = connection.createChannel();

            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 绑定
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // 发送消息
            String message = "Hello World!";
            channel.basicPublish(
                    EXCHANGE_NAME,
                    ROUTING_KEY,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("✅ 消息已发送: " + message);

        } catch (IOException | TimeoutException e) {
            System.err.println("❌ 发送消息失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResource(channel, connection);
        }
    }
}