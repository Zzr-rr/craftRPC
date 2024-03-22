package com.zhuzr.rpc.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.utils.serializer.Serializer;
import com.zhuzr.rpc.common.utils.serializer.impl.GsonSerializer;

import java.util.Arrays;

public class TestSeri {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Serializer serializer = new GsonSerializer();


    public static void main(String[] args) throws JsonProcessingException {
        RpcRequestMessage requestMessage = new RpcRequestMessage(2, "service", "sayHello", String.class, new Class[]{String.class}, new String[]{"class"});
        byte[] bytes = serializer.serialize(requestMessage);
        System.out.println(Arrays.toString(bytes));
    }
}
