package com.zhuzr.rpc.client.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.client.handler.NettyClientHandler;
import com.zhuzr.rpc.common.pojo.Invocation;
import com.zhuzr.rpc.common.pojo.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class NettyClient {
    private final Bootstrap bootstrap = new Bootstrap();
    private final NettyClientHandler clientHandler = new NettyClientHandler();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(NettyClient.class.getName());

    public NettyClient(String hostname, int port) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        // 设置线程组
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 添加通道处理器
                        ch.pipeline().addLast(clientHandler);
                    }
                });
        bootstrap.connect(hostname, port);
        logger.info("initial client server successfully.");
    }

    public String sendRequest(Invocation invocation) throws JsonProcessingException, InterruptedException {
        String deserializedInvocation = mapper.writeValueAsString(invocation);
        clientHandler.sendMsg(deserializedInvocation);
        Thread.sleep(3000);
        return clientHandler.getRepliedMsg();
    }
}
