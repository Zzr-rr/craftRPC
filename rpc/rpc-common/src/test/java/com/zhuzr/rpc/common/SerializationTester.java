package com.zhuzr.rpc.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SerializationTester {


    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        List<URL> originalList = new ArrayList<>();
        URL url = new URL();
        url.setHostname("localhost");
        url.setPort(8080);
        originalList.add(url);
        // originalList.add("test2");
        String serializedList = mapper.writeValueAsString(originalList);
        System.out.println("SerializedList:" + serializedList);
        List<URL> returnList = mapper.readValue(serializedList, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
    }
}
