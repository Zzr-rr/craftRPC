package com.zhuzr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhuzr.common.URL;
import com.zhuzr.protocol.HttpServer;
import com.zhuzr.register.LocalRegister;
import com.zhuzr.register.RemoteRegister;
import com.zhuzr.service.HelloService;
import com.zhuzr.service.impl.HelloServiceImpl;

public class provider {
    public static void main(String[] args) throws JsonProcessingException {
        // 如何接受方法的调用？接受网络请求
        // Netty, Tomcat ... 一些Servlet容器 通过用户去配置网络请求的种类
        // 首先注册服务
        URL url = new URL("localhost", 8080);
        RemoteRegister.register(HelloService.class.getName(), url);
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
