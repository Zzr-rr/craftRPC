package com.zhuzr.rpc.server.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.logging.Logger;

public class ReaderIdleHandler extends ChannelDuplexHandler {
    private static final Logger logger = Logger.getLogger("ReaderIdleHandler");

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        if (idleStateEvent.state() == IdleState.READER_IDLE) {
            logger.info("No data 5s..");
            ctx.channel().close();
        }
        super.userEventTriggered(ctx, evt);
    }
}
