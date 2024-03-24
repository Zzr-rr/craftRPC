package com.zhuzr.rpc.server.handler;

import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import com.zhuzr.rpc.common.registry.ZkMethodDiscovery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

    private static final Logger logger = Logger.getLogger("RpcRequestMessageHandler");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequestMessage rpcRequestMessage) throws Exception {
        RpcResponseMessage responseMessage = new RpcResponseMessage();
        try {
            String interfaceName = rpcRequestMessage.getInterfaceName();
            Class classImpl = ZkMethodDiscovery.lookupMethod(interfaceName, "1.0");
            Method method = classImpl.getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
            Object result = method.invoke(classImpl.newInstance(), rpcRequestMessage.getParameters());
            responseMessage.setResponseValue(result);
            responseMessage.setSequenceId(rpcRequestMessage.getRequestId());
            channelHandlerContext.writeAndFlush(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setExceptionValue(e);
        }
        channelHandlerContext.writeAndFlush(responseMessage);
    }
}
