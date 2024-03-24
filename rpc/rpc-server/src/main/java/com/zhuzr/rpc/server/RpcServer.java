package com.zhuzr.rpc.server;

import com.zhuzr.rpc.common.handler.RpcMessageCodec;
import com.zhuzr.rpc.server.handler.ConnectionHandler;
import com.zhuzr.rpc.server.handler.ReaderIdleHandler;
import com.zhuzr.rpc.server.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.logging.Logger;

public class RpcServer {
    private static final Logger logger = Logger.getLogger(RpcServer.class.getName());

    public void start(String hostname, Integer port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // TODO 加入心跳机制、服务上下线通知等相关机制。
                            socketChannel.pipeline().addLast(new IdleStateHandler(1, 0, 0));
                            socketChannel.pipeline().addLast(new ReaderIdleHandler());
                            socketChannel.pipeline().addLast(new RpcMessageCodec());
                            socketChannel.pipeline().addLast(new RpcRequestMessageHandler());
                        }
                    });
            // 绑定端口号，启动服务端 sync用于阻塞 直到服务器启动完成
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
