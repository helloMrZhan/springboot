package com.zjq.rabbitmq.basic.service.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 消息生产者示例
 * <p>
 * 本类演示如何通过 AMQP 客户端连接 RabbitMQ 服务器，
 * 声明交换机与队列，建立绑定关系，并发送一条持久化文本消息。
 * </p>
 */
public class RabbitProducer {

    // 消息交换机名称
    private static final String EXCHANGE_NAME = "exchange_demo";

    // 路由键，用于绑定队列与交换机
    private static final String ROUTING_KEY = "routingkey_demo";

    // 消息队列名称
    private static final String QUEUE_NAME = "queue_demo";

    // RabbitMQ 服务器 IP 地址
    private static final String IP_ADDRESS = "192.168.253.183";

    // RabbitMQ 服务端口（默认 5672）
    private static final int PORT = 5672;

    /**
     * 程序入口
     * 执行流程：
     * 1. 创建连接工厂并配置连接参数
     * 2. 建立与 RabbitMQ 服务器的连接
     * 3. 创建通信信道
     * 4. 声明交换机、队列并建立绑定
     * 5. 发送持久化消息
     * 6. 释放资源
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;

        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();

            // 设置 RabbitMQ 服务器地址
            factory.setHost(IP_ADDRESS);

            // 设置服务端口
            factory.setPort(PORT);

            // 设置认证信息
            factory.setUsername("root");
            factory.setPassword("KfOXpGSwU7iK");

            // 建立连接
            connection = factory.newConnection();

            // 创建通信信道
            channel = connection.createChannel();

            /*
             * 声明一个 direct 类型的交换机
             * 参数说明：
             * - 交换机名称
             * - 类型（direct）
             * - 是否持久化（true：重启后仍存在）
             * - 是否自动删除（false：不自动删除）
             * - 其他参数（null）
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);

            /*
             * 声明一个队列
             * 参数说明：
             * - 队列名称
             * - 是否持久化
             * - 是否排他（false：可被多个连接使用）
             * - 是否自动删除
             * - 其他参数（null）
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            /*
             * 绑定队列到交换机
             * 消息将根据 routingKey 路由到指定队列
             */
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // 要发送的消息内容
            String message = "Hello World!";

            /*
             * 发布消息
             * 使用持久化文本消息属性，确保消息可持久化存储
             */
            channel.basicPublish(
                EXCHANGE_NAME,
                ROUTING_KEY,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes(StandardCharsets.UTF_8)
            );

            // 输出发送成功提示
            System.out.println("✅ 消息已发送: " + message);

        } catch (IOException | TimeoutException e) {
            // 处理连接或 IO 异常
            System.err.println("❌ 发生异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源被正确释放
            closeResource(channel, connection);
        }
    }

    /**
     * 安全关闭 Channel 和 Connection
     *
     * @param channel    要关闭的信道
     * @param connection 要关闭的连接
     */
    private static void closeResource(Channel channel, Connection connection) {
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException | TimeoutException e) {
                System.err.println("❌ 关闭 Channel 时发生异常: " + e.getMessage());
            }
        }

        if (connection != null && connection.isOpen()) {
            try {
                connection.close();
            } catch (IOException e) {
                System.err.println("❌ 关闭 Connection 时发生异常: " + e.getMessage());
            }
        }
    }
}