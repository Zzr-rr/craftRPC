package com.zhuzr.rpc.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    ChannelHandlerContext ctx;
    String repliedMsg;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        this.repliedMsg = byteBuf.toString(CharsetUtil.UTF_8);
    }

    public void sendMsg(String msg) {
        this.ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    public String getRepliedMsg() {
        return this.repliedMsg;
    }
}
