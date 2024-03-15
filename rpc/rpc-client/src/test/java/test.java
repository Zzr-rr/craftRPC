import com.zhuzr.rpc.client.RpcClient;
import io.netty.channel.Channel;

public class test {
    public static void main(String[] args) {
        Channel channel = RpcClient.getChannel("localhost", 8080);
        channel.writeAndFlush("1213213");
    }
}
