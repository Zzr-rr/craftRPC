package com.zhuzr.rpc.common.utils.serializer.impl;

import com.zhuzr.rpc.common.utils.serializer.Serializer;

public class HessianSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
        return null;
    }
}
