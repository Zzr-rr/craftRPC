import com.zhuzr.rpc.common.utils.ProcotolFrameDecoder;
import com.zhuzr.rpc.common.utils.RpcMessageCodec;
import com.zhuzr.rpc.server.RpcServer;
import com.zhuzr.rpc.server.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.Logger;

public class MyServer {
    public static void main(String[] args) throws Exception {
        String hostname = "localhost";
        int port = 8080;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        RpcMessageCodec rpcMessageCodec = new RpcMessageCodec();
        RpcRequestMessageHandler rpcRequestMessageHandler = new RpcRequestMessageHandler();
        try {
            // 创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("init Channel");
                            socketChannel.pipeline().addLast(new ProcotolFrameDecoder(1024, 12, 4, 0, 0));
                            socketChannel.pipeline().addLast(new LoggingHandler());
                            socketChannel.pipeline().addLast(rpcMessageCodec);
                            // 给pipeline管道设置处理器
                            socketChannel.pipeline().addLast(rpcRequestMessageHandler);
                        }
                    }); // 给workerGroup的EventLoop对应的管道设置处理器
            // 绑定端口号，启动服务端
            Channel channel = bootstrap.bind(8080).sync().channel();
            System.out.println("netty ready to start, hostname is " + hostname + " ,port is " + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("server error" + e);
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}