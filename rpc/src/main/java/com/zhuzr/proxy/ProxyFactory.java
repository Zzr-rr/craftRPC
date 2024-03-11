package com.zhuzr.proxy;

import com.zhuzr.common.Invocation;
import com.zhuzr.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
                String result = httpClient.send("localhost", 8080, invocation);
                return result;
            }
        });

        return (T) proxyInstance;
    }
}
