package com.zhuzr.rpc.common.utils.serializer;

public interface Serializer {
    <T> byte[] serialize(T object);

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception;
}
