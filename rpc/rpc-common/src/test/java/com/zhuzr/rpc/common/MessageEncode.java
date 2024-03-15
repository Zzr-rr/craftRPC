package com.zhuzr.rpc.common;

import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.utils.RpcMessageCodec;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class MessageEncode {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new RpcMessageCodec());
        RpcRequestMessage requestMessage = new RpcRequestMessage(1, "hello", "method", String.class, null, null);
        channel.writeOutbound(requestMessage);
    }
}
