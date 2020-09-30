package com.dxxt.im.websocket;

import com.dxxt.im.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static final TextFrameHandler INSTANCE = new TextFrameHandler();

    public static final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.debug(msg.text());
        Map<String, Object> data = jsonMapper.readValue(msg.text(), Map.class);

        String type = (String) data.get("type");
        if(type.equals("login")) {
            //认证失败-关闭socket
            String jwt = (String) data.get("jwt");
            String userId = JwtTokenUtil.verifyToken(jwt);
            if(userId == null) {
                log.debug("客户端身份认证失败 关闭Channel={}", ctx.channel().remoteAddress());
                ctx.channel().close();
                return;
            }

            //认证成功-取消定时任务
            ScheduledFuture future = ctx.channel().attr(WsEventHandler.futureKey).getAndSet(null);
            log.debug("客户端身份认证成功 取消身份验证任务..");
            if(future != null) {
                future.cancel(true);
            }

            //tcp-channel 绑定用户id
            log.debug("客户端绑定用户ID  channelId={} userId={}", ctx.channel().id().asLongText(), userId);
            ctx.channel().attr(ServerUtils.userIDkey).set(userId);
        }

    }
}
