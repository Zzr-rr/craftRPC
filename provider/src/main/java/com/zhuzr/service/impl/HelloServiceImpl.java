package com.zhuzr.service.impl;
import com.zhuzr.rpc.common.annotations.RpcService;
import com.zhuzr.service.HelloService;

@RpcService(serviceName = "HelloService", version = "1.0")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "Hello " + msg;
    }

    public int calSum(int a, int b){
        return a + b;
    }
}
