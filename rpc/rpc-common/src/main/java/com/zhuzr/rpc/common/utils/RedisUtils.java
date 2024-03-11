package com.zhuzr.rpc.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisUtils {
    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);
    private static final ObjectMapper mapper = new ObjectMapper();

    public RedisUtils() {
        jedis.auth("123456");
    }

    public List<URL> getURLs(String key) throws JsonProcessingException {
        String value = jedis.get(key);
        List<URL> deserializedUrls = new ArrayList<>();
        if (value != null)
            deserializedUrls = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
        return deserializedUrls;
    }

    public void putURLs(String key, List<URL> url) throws JsonProcessingException {
        String value = mapper.writeValueAsString(url);
        jedis.set(key, value);
    }
}
