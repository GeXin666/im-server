package com.dxxt.im.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static String zookeeperUrl;
    public static String zookeeperPath;
    public static String imServerIp;
    public static int imServerPort;
    public static String redisIp;
    public static String jwtSecret;
    public static String redisChannel;
    public static String rabbitMqIp;
    public static int rabbitMqHost;
    public static String rabbitMqQueueName;

    @Value("${im.zookeeper.url}")
    public void setZookeeperUrl(String zookeeperUrl) {
        AppConfig.zookeeperUrl = zookeeperUrl;
    }

    @Value("${im.zookeeper.path}")
    public void setZookeeperPath(String zookeeperPath) {
        AppConfig.zookeeperPath = zookeeperPath;
    }

    @Value("${im.server.ip}")
    public void setImServerIp(String imServerIp) {
        AppConfig.imServerIp = imServerIp;
    }

    @Value("${im.server.port}")
    public void setImServerPort(int imServerPort) {
        AppConfig.imServerPort = imServerPort;
    }

    @Value("${im.redis.ip}")
    public void setRedisIp(String redisIp) {
        AppConfig.redisIp = redisIp;
    }

    @Value("${im.jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        AppConfig.jwtSecret = jwtSecret;
    }

    @Value("${im.rabbitmq.ip}")
    public void setRabbitMqIp(String rabbitMqIp) {
        AppConfig.rabbitMqIp = rabbitMqIp;
    }

    @Value("${im.rabbitmq.host}")
    public void setRabbitMqHost(int rabbitMqHost) {
        AppConfig.rabbitMqHost = rabbitMqHost;
    }

    @Value("${im.rabbitmq.queue}")
    public void setRabbitMqQueueName(String rabbitMqQueueName) {
        AppConfig.rabbitMqQueueName = rabbitMqQueueName;
    }

    @Value("${im.redis.channel}")
    public void setRedisChannel(String redisChannel) {
        AppConfig.redisChannel = redisChannel;
    }

}
