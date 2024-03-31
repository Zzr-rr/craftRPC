package com.zhuzr.rpc.server;

import com.zhuzr.rpc.common.handler.RpcMessageCodec;
import com.zhuzr.rpc.server.handler.ReaderIdleHandler;
import com.zhuzr.rpc.server.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.logging.Logger;

public class RpcServer {
    private static final Logger logger = Logger.getLogger(RpcServer.class.getName());

    public void start(String hostname, Integer port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new IdleStateHandler(1, 0, 0));
                            socketChannel.pipeline().addLast(new ReaderIdleHandler());
                            socketChannel.pipeline().addLast(new RpcMessageCodec());
                            socketChannel.pipeline().addLast(new RpcRequestMessageHandler());
                        }
                    });
            Channel channel = bootstrap.bind(8080).sync().channel();
            logger.info("netty ready is already starting, hostname is " + hostname + " ,port is " + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.info("server error" + e);
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
