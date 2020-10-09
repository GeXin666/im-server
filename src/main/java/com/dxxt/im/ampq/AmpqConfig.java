package com.dxxt.im.ampq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmpqConfig {

    public static final String messgaeQueue = "IM-SERVER-A";
    public static final String exchangeName = "amq.direct";

    @Bean
    public Queue messageQueue() {
        return new Queue(messgaeQueue, true);
    }

    @Bean
    Binding marketingBinding(Queue messageQueue) {
        return BindingBuilder.bind(messageQueue).to(new DirectExchange(exchangeName)).with(messgaeQueue);
    }
}
