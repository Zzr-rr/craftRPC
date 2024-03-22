package com.zhuzr;


import com.zhuzr.rpc.client.proxy.ProxyFactory;
import com.zhuzr.service.HelloService;

public class Consumer {
    public static void main(String[] args) throws InterruptedException {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("hello");
        System.out.println("received:" + result);
    }
}
