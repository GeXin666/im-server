package com.dxxt.im.websocket;

import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerUtils {

    public final static AttributeKey<String> userIDkey = AttributeKey.valueOf("ServerUtils-USERID");

    //channel容器
    public final static DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //推送消息
    public static void sendMessgae(final String clientId, final String message) {
        channels.writeAndFlush(new TextWebSocketFrame(message), channel -> clientId.equals(channel.attr(userIDkey).get()));
    }
}
