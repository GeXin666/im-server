package com.dxxt.im.websocket;

import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerUtils {

    public final static AttributeKey<String> userIDkey = AttributeKey.valueOf("ServerUtils-USERID");

}
