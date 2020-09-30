package com.dxxt.im.websocket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;

import java.util.concurrent.TimeUnit;

@Slf4j
@Sharable
public class WsEventHandler extends ChannelInboundHandlerAdapter {

	public final static WsEventHandler INSTANCE = new WsEventHandler();

	public final static AttributeKey<ScheduledFuture> futureKey = AttributeKey.valueOf("WsEventHandler-ScheduledFuture");

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
		if (event instanceof HandshakeComplete) {
			log.debug("client -> " + ctx.channel().remoteAddress() + " HandshakeComplete " + "->" + event);
			ctx.channel().pipeline().addLast("textHandler", TextFrameHandler.INSTANCE);

			//握手成功以后，启动心跳定时任务。
			final ScheduledFuture future = ctx.channel().eventLoop().scheduleWithFixedDelay(()->{
				ctx.channel().writeAndFlush(new PingWebSocketFrame());
			}, 10, 10, TimeUnit.SECONDS);

			//在channel的关闭事件，添加回调方法，取消心跳任务.
			ctx.channel().closeFuture().addListener((ChannelFutureListener) f -> {
				future.cancel(true);
			});

        } else {
            super.userEventTriggered(ctx, event);
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("新客户端Tcp连接成功,启动认证计时器...");

		//启动一个定时任务,60秒以后关闭socket
		ScheduledFuture future = ctx.executor().schedule(()->{
			ctx.channel().close();
		}, 60, TimeUnit.SECONDS);

		//把任务句柄和channel绑定在一起
		ctx.channel().attr(futureKey).set(future);

		//向下传递事件
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//获取关闭任务句柄对象
		ScheduledFuture future = ctx.channel().attr(futureKey).getAndSet(null);
		if(future != null) {
			//取消任务
			future.cancel(true);
		}
		super.channelInactive(ctx);
	}
}
