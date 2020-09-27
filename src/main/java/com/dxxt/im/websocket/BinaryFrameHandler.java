package com.dxxt.im.websocket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

	public final static BinaryFrameHandler INSTANCE = new BinaryFrameHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.warn("exceptionCaught ->" + ctx.channel().remoteAddress(), cause);
		ctx.channel().close();
	}
}
