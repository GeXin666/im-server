package com.dxxt.im.websocket;

import lombok.extern.slf4j.Slf4j;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Slf4j
@Sharable
public class HeartbeatHanlder extends ChannelInboundHandlerAdapter {

    public final static HeartbeatHanlder INSTANCE = new HeartbeatHanlder();
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
            	log.info("Read Idel -> " + state + " clientAddress -> " + ctx.channel().remoteAddress());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
	}

}
