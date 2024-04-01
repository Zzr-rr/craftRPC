package com.zhuzr.service.impl;

import com.zhuzr.rpc.common.annotations.RpcService;
import com.zhuzr.service.MathService;

@RpcService(serviceName = "MathService", version = "1.0")
public class MathServiceImpl implements MathService {
    @Override
    public int testMath(int c) {
        return c * 10;
    }
}
