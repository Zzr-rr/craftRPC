package com.zhuzr.rpc.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.ServiceAddress;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisUtils {
    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);
    private static final ObjectMapper mapper = new ObjectMapper();

    public RedisUtils() {
        jedis.auth("123456");
    }

    public List<ServiceAddress> getURLs(String key) throws JsonProcessingException {
        String value = jedis.get(key);
        List<ServiceAddress> deserializedServiceAddresses = new ArrayList<>();
        if (value != null)
            deserializedServiceAddresses = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, ServiceAddress.class));
        return deserializedServiceAddresses;
    }

    public void putURLs(String key, List<ServiceAddress> serviceAddress) throws JsonProcessingException {
        String value = mapper.writeValueAsString(serviceAddress);
        jedis.set(key, value);
    }
}
