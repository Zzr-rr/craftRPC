package com.zhuzr;


import com.zhuzr.rpc.client.proxy.ProxyFactory;
import com.zhuzr.service.HelloService;

import java.util.ArrayList;
import java.util.List;

public class Consumer {
    public static void main(String[] args) throws InterruptedException {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            results.add(helloService.sayHello("Hello" + i));
            System.out.println("received msg:" + results);
        }
    }
}
