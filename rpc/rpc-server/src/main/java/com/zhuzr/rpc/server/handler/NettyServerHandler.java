package com.zhuzr.rpc.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.Invocation;
import com.zhuzr.rpc.common.registry.ZkMethodDiscovery;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        String receiveMsg = byteBuf.toString(CharsetUtil.UTF_8);
        Invocation invocation = null;
        try {
            invocation = mapper.readValue(receiveMsg, Invocation.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (invocation == null) {
            return;
        }
        String interfaceName = invocation.getInterfaceName();
        // 读取默认的版本号
        Class classImpl = ZkMethodDiscovery.lookupMethod(interfaceName, "1.0");
        Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

        ctx.writeAndFlush(Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常，关闭通道
        cause.printStackTrace();
        ctx.close();
    }
}
