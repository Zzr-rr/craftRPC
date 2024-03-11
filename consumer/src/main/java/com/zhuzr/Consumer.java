package com.zhuzr;

import com.zhuzr.common.Invocation;
import com.zhuzr.protocol.HttpClient;
import com.zhuzr.service.HelloService;

public class Consumer {

    public static void main(String[] args) {
        // HelloService helloService = ?;
        // helloService.SayHello("zhuzr");

        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
                new Class[]{String.class}, new Object[]{"callback"});

        // 通过Http发送相应的请求
        HttpClient httpClient = new HttpClient();
        String result = httpClient.send("localhost", 8080, invocation);
        System.out.println(result);
    }
}
