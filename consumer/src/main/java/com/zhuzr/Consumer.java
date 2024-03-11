package com.zhuzr;

import com.zhuzr.proxy.ProxyFactory;
import com.zhuzr.service.HelloService;

public class Consumer {

    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("hello");
        System.out.println(result);
    }
}
