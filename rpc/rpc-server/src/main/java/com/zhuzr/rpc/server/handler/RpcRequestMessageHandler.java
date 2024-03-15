package com.zhuzr.rpc.server.handler;

import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import com.zhuzr.rpc.common.registry.ZkMethodDiscovery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequestMessage rpcRequestMessage) throws Exception {
        System.out.println("server received rpcRequestMessage:" + rpcRequestMessage);
        RpcResponseMessage responseMessage = new RpcResponseMessage();
        // 读取到相应的函数后向管道中写入信息
        try {
            String interfaceName = rpcRequestMessage.getInterfaceName();
            // 读取默认的版本号
            Class classImpl = ZkMethodDiscovery.lookupMethod(interfaceName, "1.0");
            Method method = classImpl.getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
            Object result = method.invoke(classImpl.newInstance(), rpcRequestMessage.getParameters());
            responseMessage.setResponseValue(result);
            channelHandlerContext.writeAndFlush(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setExceptionValue(e);
        }
        channelHandlerContext.writeAndFlush(responseMessage);
    }
}
