package com.zhuzr.service.impl;

import com.zhuzr.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String msg) {
        return "Hello " + msg;
    }
}
