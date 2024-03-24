package com.zhuzr.rpc.client.handler;

import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger("RpcResponseMessageHandler");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage rpcResponseMessage) throws Exception {
        Promise<Object> promise = PROMISES.get(rpcResponseMessage.getSequenceId());
        if (promise != null) {
            Object returnValue = rpcResponseMessage.getResponseValue();
            Exception exceptionValue = rpcResponseMessage.getExceptionValue();
            if (exceptionValue != null) {
                promise.setFailure(exceptionValue);
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}
