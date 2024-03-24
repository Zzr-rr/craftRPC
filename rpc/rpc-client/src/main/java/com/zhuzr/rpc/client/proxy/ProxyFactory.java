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
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ProxyFactory {
    private static final Logger logger = Logger.getLogger("ProxyFactory");

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
                Channel channel = RpcClient.getChannel(serviceAddress.getHostname(), serviceAddress.getPort());
                channel.writeAndFlush(requestMessage)
                        .addListener(promise -> {
                            if (!promise.isSuccess()) {
                                logger.info(String.valueOf(promise.cause()));
                            }
                        });

                DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
                RpcResponseMessageHandler.PROMISES.put(requestMessage.getRequestId(), promise);
                promise.await();
                if (!promise.isSuccess()) {
                    throw new RuntimeException(promise.cause());
                }
                return promise.getNow();
            }
        });

        return (T) proxyInstance;
    }
}