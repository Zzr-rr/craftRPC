package com.zhuzr;


import com.zhuzr.rpc.client.proxy.ProxyFactory;
import com.zhuzr.service.HelloService;
import com.zhuzr.service.MathService;

import java.util.ArrayList;
import java.util.List;

public class Consumer {
    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        MathService mathService = ProxyFactory.getProxy(MathService.class);
        for(int i = 0; i < 10; ++i){
            System.out.println("test hello method1:" + helloService.sayHello("hello"));
            System.out.println("test hello method2:" + helloService.calSum(10, 20));
            System.out.println("test math method1:" + mathService.testMath(10));
        }
    }
}
