package com.zhuzr.rpc.client;

import com.zhuzr.rpc.client.handler.RpcResponseMessageHandler;
import com.zhuzr.rpc.client.handler.TestHandler;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.utils.ProcotolFrameDecoder;
import com.zhuzr.rpc.common.utils.RpcMessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.tomcat.util.net.openssl.ciphers.Protocol;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
public class RpcClient {
    private static Channel channel = null;
    private static final Object LOCK = new Object();

    private static void initChannel(String hostname, int port) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("ready to send");
                            // ch.pipeline().addLast(new ProcotolFrameDecoder(1024, 12, 4, 0, 0));
                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(new RpcMessageCodec());
                            ch.pipeline().addLast(new RpcResponseMessageHandler());
                        }
                    });
            // 异步执行连接的操作
            channel = bootstrap.connect(hostname, port).sync().channel();
            RpcRequestMessage message = new RpcRequestMessage(1, "com.zhuzr.service.HelloService", "sayHello", String.class, new Class[]{String.class}, new Object[]{"hello"});

            // ---- 生成测试数据 ----
            ByteBuf buf = Unpooled.buffer(256);
            String testData = "Hello, Netty!";
            buf.writeBytes(testData.getBytes(Charset.forName("UTF-8")));
            // ---------------------
            // 信号其实是成功发送出去了，也没有经过decode，所以说接下来应该排除服务器端的bug。
            ChannelFuture channelFuture = channel.writeAndFlush(message)
                    .addListener(promise -> {
                        if (!promise.isSuccess()) {
                            System.out.println(promise.cause());
                        } else {
                            System.out.println("success");
                        }
                    });

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
