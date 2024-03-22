package com.zhuzr.rpc.common.utils.serializer;

import com.zhuzr.rpc.common.config.RpcMessageCodecConfig;
import com.zhuzr.rpc.common.utils.serializer.impl.JacksonSerializer;
import com.zhuzr.rpc.common.utils.serializer.impl.HessianSerializer;

public class SerializerFactory {
    public static Serializer getSerializer(int order) {
        if (order == RpcMessageCodecConfig.HESSIAN) {
            return new HessianSerializer();
        } else if (order == RpcMessageCodecConfig.JACKSON) {
            return new JacksonSerializer();
        }
        // 该工厂类默认返回Jackson序列化器
        return new JacksonSerializer();
    }
}
