package com.zhuzr.rpc.server.protocol;

import com.zhuzr.rpc.server.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Logger;

public class NettyServer {
    // 创建两个线程组 boosGroup、workerGroup
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private static final Logger logger = Logger.getLogger(NettyServer.class.getName());

    public NettyServer() {
    }

    public void start(String hostname, Integer port) {
        logger.info("netty ready to start, hostname is " + hostname + " ,port is " + port);
        try {
            // 创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 给pipeline管道设置处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给workerGroup的EventLoop对应的管道设置处理器
            // 绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
