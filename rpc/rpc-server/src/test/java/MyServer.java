import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.zookeeper.data.Id;

public class MyServer {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8080;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 用来判断是否读空闲时间过长，或者写空闲时间过长
                            // 5秒内没有收到channel的数据，会触发一个事件 IdleState#READER_IDLE
                            socketChannel.pipeline().addLast(new IdleStateHandler(6, 6, 0));
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            socketChannel.pipeline().addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    if (event.state() == IdleState.READER_IDLE) {
                                        System.out.println("已经5s没有读到数据了");
                                    }
                                }
                            });
                            socketChannel.pipeline().addLast(new LoggingHandler());
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println(msg);
                                    super.channelRead(ctx, msg);
                                }
                            });
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