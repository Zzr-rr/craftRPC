package com.zhuzr.rpc.common.utils.serializer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.utils.serializer.Serializer;

import java.io.*;

public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> byte[] serialize(T object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (IOException e) {
            System.out.println("序列化失败" + e);
            throw new RuntimeException("序列化失败", e);
        }
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            System.out.println("反序列化失败" + e);
            throw new RuntimeException("反序列化失败", e);
        }
    }

}
