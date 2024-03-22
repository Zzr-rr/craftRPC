package com.zhuzr.rpc.client.proxy;


import com.zhuzr.rpc.client.handler.RpcResponseMessageHandler;
import com.zhuzr.rpc.client.RpcClient;
import com.zhuzr.rpc.common.loadbalance.LoadBalancer;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.ServiceAddress;
import com.zhuzr.rpc.common.registry.ZkServiceDiscovery;
import com.zhuzr.rpc.common.utils.SequenceGenerator;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;

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
                int sequenceId = SequenceGenerator.getNextSequence();
                RpcRequestMessage requestMessage = new RpcRequestMessage(sequenceId, interfaceClass.getName(), method.getName(), method.getReturnType(),
                        method.getParameterTypes(), args);
                // 服务发现 | 负载均衡 | 服务容灾
                List<ServiceAddress> list = ZkServiceDiscovery.lookupService(interfaceClass.getName(), "1.0");
                ServiceAddress serviceAddress = LoadBalancer.random(list);

                // 获取通讯的通道
                Channel channel = RpcClient.getChannel(serviceAddress.getHostname(), serviceAddress.getPort());
                // 发送消息
                channel.writeAndFlush(requestMessage)
                        .addListener(promise -> {
                            if (!promise.isSuccess()) {
                                System.out.println(promise.cause());
                            } else {
                                System.out.println("Client send successfully.");
                            }
                        });
                DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
                RpcResponseMessageHandler.PROMISES.put(sequenceId, promise);
                // 等待 promise 的结果
                promise.await(); // 如果成功或失败 都不会抛出异常
                if (!promise.isSuccess()) {
                    throw new RuntimeException(promise.cause());
                }
                return promise.getNow();
            }
        });

        return (T) proxyInstance;
    }
}