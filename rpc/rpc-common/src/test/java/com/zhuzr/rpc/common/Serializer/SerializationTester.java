package com.zhuzr.rpc.common.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.ServiceAddress;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SerializationTester {


    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        List<ServiceAddress> originalList = new ArrayList<>();
        ServiceAddress serviceAddress = new ServiceAddress();
        serviceAddress.setHostname("localhost");
        serviceAddress.setPort(8080);
        originalList.add(serviceAddress);
        // originalList.add("test2");
        String serializedList = mapper.writeValueAsString(originalList);
        System.out.println("SerializedList:" + serializedList);
        List<ServiceAddress> returnList = mapper.readValue(serializedList, mapper.getTypeFactory().constructCollectionType(List.class, ServiceAddress.class));
    }
}
