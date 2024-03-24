package com.zhuzr.rpc.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

public class ConnectionHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger("ConnectionHandler");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client connected: " + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Client disconnected: " + ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("received msg : " + msg.toString());
        super.channelRead(ctx, msg);
    }
}
