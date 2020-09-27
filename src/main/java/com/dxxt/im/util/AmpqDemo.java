package com.dxxt.im.util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AmpqDemo {

    public static void main(String[] agrs) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.80.111");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            String message = "Hello World!";
            channel.basicPublish("", "ha.hello", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
