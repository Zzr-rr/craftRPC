package com.zhuzr;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.registry.ZkMethodRegistry;
import com.zhuzr.rpc.common.registry.ZkServiceRegistry;
import com.zhuzr.rpc.server.protocol.HttpServer;
import com.zhuzr.service.HelloService;
import com.zhuzr.service.impl.HelloServiceImpl;

public class Provider {
    public static void main(String[] args) throws JsonProcessingException {
        // 如何接受方法的调用？接受网络请求
        // Netty, Tomcat ... 一些Servlet容器 通过用户去配置网络请求的种类
        // 首先注册服务
        URL url = new URL("localhost", 8080);
        ZkServiceRegistry.registerService(HelloService.class.getName(), "1.0", url);
        ZkMethodRegistry.registerMethod(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        // LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
