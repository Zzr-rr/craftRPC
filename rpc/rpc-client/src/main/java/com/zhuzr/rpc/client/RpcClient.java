package com.zhuzr.rpc.client;

import com.zhuzr.rpc.client.handler.RpcResponseMessageHandler;
import com.zhuzr.rpc.common.handler.RpcMessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.Logger;

@ChannelHandler.Sharable
public class RpcClient {
    private static Channel channel = null;
    private static final Object LOCK = new Object();
    private static Logger logger = Logger.getLogger("RpcClient Logger");

    private static void initChannel(String hostname, int port) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            logger.info("client connected, ready to send ...");
                            // TODO 加入心跳机制、服务上下线通知等相关机制。
                            ch.pipeline().addLast(new RpcMessageCodec());
                            ch.pipeline().addLast(new RpcResponseMessageHandler());
                        }
                    });
            // 异步执行连接的操作
            channel = bootstrap.connect(hostname, port).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取唯一的 channel 对象
    public static Channel getChannel(String localhost, int port) {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel(localhost, port);
            System.out.println("channel = " + channel);
            return channel;
        }
    }

    public static void main(String[] args) {
        initChannel("localhost", 8080);
    }
}
