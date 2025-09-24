package com.zjq.rabbitmq.basic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 配置属性类
 * @author zjq
 */
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {

    private String host;
    private String port;
    private String username;
    private String password;

    // Getters and Setters

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RabbitMQProperties{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='******'" + // 注意：密码不打印明文
                '}';
    }
}