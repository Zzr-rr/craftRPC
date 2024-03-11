package com.zhuzr.proxy;

import com.zhuzr.common.Invocation;
import com.zhuzr.common.URL;
import com.zhuzr.loadbalance.LoadBalancer;
import com.zhuzr.protocol.HttpClient;
import com.zhuzr.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {
    public static <T> T getProxy(Class interfaceClass) {
        // 用户配置
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);

                // 通过Http发送相应的请求
                HttpClient httpClient = new HttpClient();

//                // 服务发现
//                List<URL> list = RemoteRegister.get(interfaceClass.getName());
//                // 负载均衡
//                URL url = LoadBalancer.random(list);

                return httpClient.send("localhost", 8080, invocation);
            }
        });

        return (T) proxyInstance;
    }
}
