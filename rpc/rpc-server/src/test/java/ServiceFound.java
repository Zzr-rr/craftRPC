import com.zhuzr.rpc.common.pojo.Invocation;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.registry.ZkMethodDiscovery;
import com.zhuzr.rpc.common.registry.ZkMethodRegistry;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceFound {
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        RpcRequestMessage message = new RpcRequestMessage(1, "com.zhuzr.service.HelloService", "sayHello", String.class, new Class[]{String.class}, new Object[]{"hello"});
        RpcRequestMessage rpcRequestMessage = message;
        // 读取到相应的函数后向管道中写入信息
        String interfaceName = rpcRequestMessage.getInterfaceName();
        // 读取默认的版本号
        Class classImpl = ZkMethodDiscovery.lookupMethod(interfaceName, "1.0");
        System.out.println(classImpl);
        Method method = classImpl.getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
        Object result = (String) method.invoke(classImpl.newInstance(), rpcRequestMessage.getParameters());
        System.out.println(result);
    }
}
