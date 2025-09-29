package com.zjq.rabbitmq.basic.service.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 生产者相关代码清单
 * @author zjq
 */
public class RabbitProducerCoder {
    // 消息交换机名称
    private static final String EXCHANGE_NAME = "exchange_demo";

    // 路由键，用于绑定队列与交换机
    private static final String ROUTING_KEY = "routingkey_demo";

    // 消息队列名称
    private static final String QUEUE_NAME = "queue_demo";

    // RabbitMQ 服务器 IP 地址
    private static final String HOST = "127.0.0.1";

    // RabbitMQ 服务端口（默认 5672）
    private static final int PORT = 5672;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "KfOXpGSwU7iK";
    private static final String VIRTUAL_HOST = "/";

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
            // --- 代码清单3-1
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            // 设置 RabbitMQ 服务器地址
            factory.setHost(HOST);
            // 设置服务端口
            factory.setPort(PORT);
            // 设置认证信息
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            // 设置虚拟主机
            factory.setVirtualHost(VIRTUAL_HOST);

            // --- 代码清单3-2
            // 创建连接工厂
//            ConnectionFactory factory = new ConnectionFactory();
//            // uri方式连接
//            factory.setUri("amqp://userName:password@host:port/virtualHost");

            // --- 代码清单3-6
//            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
//            String queueName = channel.queueDeclare().getQueue();
//            channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

            // --- 代码清单3-7
//            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            // --- 代码清单3-8
//            channel.exchangeDeclare("source", "direct", false, true, null);
//            channel.exchangeDeclare("destination", "fanout", false, true, null);
//            channel.exchangeBind("destination", "source", "exKey");
//            channel.queueDeclare("queue", false, false, true, null);
//            channel.queueBind("queue", "destination", "");
//            channel.basicPublish("source", "exKey", null, "exToDemo".getBytes());

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

            // 代码清单4-1 当mandatory参数设为true时，交换器无法根据自身的类型和路由键找到一个符合条件的队列，
            // 那么RabbitMQ会调用Basic.Return命令将消息返回给生产者
//            channel.basicPublish(EXCHANGE_NAME, "", true,
//                    MessageProperties.PERSISTENT_TEXT_PLAIN,
//                    "mandatory test".getBytes());
//
//            channel.addReturnListener(new ReturnListener() {
//                @Override
//                public void handleReturn(int replyCode, String replyText,
//                                         String exchange, String routingKey,
//                                         AMQP.BasicProperties basicProperties,
//                                         byte[] body) throws IOException {
//                    String message = new String(body);
//                    System.out.println("Basic.Return 返回的结果是: " + message);
//                }
//            });

            // 消息的投递模式（delivery mode）设置为2，即消息会被持久化（即存入磁盘）在服务器中。
            // 同时这条消息的优先级（priority）设置为1，content-type为“text/plain”。
//            channel.basicPublish(
//                    EXCHANGE_NAME,
//                    ROUTING_KEY,
//                    new AMQP.BasicProperties().builder()
//                    .deliveryMode(2)
//                    .priority(1)
//                    .userId("1")
//                    .contentType("text/plain")
//                    .build(),
//                    message.getBytes(StandardCharsets.UTF_8)
//            );

            // 发送一条带有headers的消息：
//            channel.basicPublish(
//                    EXCHANGE_NAME,
//                    ROUTING_KEY,
//                    new AMQP.BasicProperties().builder()
//                    .headers(new HashMap<String, Object>() {{
//                        put("key1", "value1");
//                        put("key2", "value2");
//                    }})
//                    .build(),
//                    message.getBytes(StandardCharsets.UTF_8)
//            );

            // 发送一条带有过期时间（expiration）的消息
//            channel.basicPublish(
//                    EXCHANGE_NAME,
//                    ROUTING_KEY,
//                    new AMQP.BasicProperties().builder()
//                    .expiration("10000")
//                    .build(),
//                    message.getBytes(StandardCharsets.UTF_8)
//            );

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

    // 代码清单3-4 错误地使用isOpen方法
    public void brokenMethod(Channel channel) throws IOException {
        if (channel.isOpen())
        {
            // The following code depends on the channel being in open state.
            // However there is a possibility of the change in the channel state
            // between isOpen() and basicQos(1) call
            // 这段注释指出了一个 竞态条件（race condition）：
            // isOpen() 返回 true 后，在调用 basicQos(1) 之前，信道可能被关闭了（例如网络中断、服务器断开等）。
            // 所以即使 isOpen() 为真，也不能保证后续操作一定成功。
            channel.basicQos(1);
        }
    }

    // --- 代码清单3-5
    public void validMethod(Channel channel)
    {
        try {
            channel.basicQos(1);
        } catch (ShutdownSignalException sse) {
            // possibly check if channel was closed
            // by the time we started action and reasons for
            // closing it
        } catch (IOException ioe) {
            // check why connection was closed
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