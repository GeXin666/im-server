package com.dxxt.im.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("log", new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("idleStateHandler", new IdleStateHandler(30, 30, 0, TimeUnit.SECONDS));
        pipeline.addLast("idleStateTrigger", HeartbeatHanlder.INSTANCE);

        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());

        WebSocketServerProtocolConfig wsConfig = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath("/websocket")
                .maxFramePayloadLength(Integer.MAX_VALUE)
                .checkStartsWith(true).build();
        pipeline.addLast("webSocketHandler", new WebSocketServerProtocolHandler(wsConfig));

        pipeline.addLast("WsEventHandler", WsEventHandler.INSTANCE);
    }
}
