package com.zhuzr;

import com.zhuzr.rpc.server.proxy.RpcServerInitializer;


public class Provider {
    public static void main(String[] args){
        RpcServerInitializer rpcServerInitializer = new RpcServerInitializer();
        rpcServerInitializer.start();
    }
}
