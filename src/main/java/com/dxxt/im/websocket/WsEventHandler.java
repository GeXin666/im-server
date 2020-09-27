package com.dxxt.im.websocket;

import lombok.extern.slf4j.Slf4j;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;

@Slf4j
@Sharable
public class WsEventHandler extends ChannelInboundHandlerAdapter {

	public final static WsEventHandler INSTANCE = new WsEventHandler();

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
		if (event instanceof HandshakeComplete) {
			log.debug("client -> " + ctx.channel().remoteAddress() + " HandshakeComplete " + "->" + event);
        } else {
            super.userEventTriggered(ctx, event);
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug(ctx.channel().remoteAddress().toString());
		super.channelActive(ctx);
	}
}
