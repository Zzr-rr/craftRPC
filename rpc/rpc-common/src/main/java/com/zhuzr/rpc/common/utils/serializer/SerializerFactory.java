package com.zhuzr.rpc.common.utils.serializer;

import com.google.gson.Gson;
import com.zhuzr.rpc.common.config.RpcMessageCodecConfig;
import com.zhuzr.rpc.common.utils.serializer.impl.GsonSerializer;
import com.zhuzr.rpc.common.utils.serializer.impl.HessianSerializer;

public class SerializerFactory {
    public static Serializer getSerializer(int order) {
        if (order == RpcMessageCodecConfig.HESSIAN) {
            return new HessianSerializer();
        } else if (order == RpcMessageCodecConfig.GSON) {
            return new GsonSerializer();
        }
        // 该工厂类默认返回Gson序列化器
        return new GsonSerializer();
    }
}
