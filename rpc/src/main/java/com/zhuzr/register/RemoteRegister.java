package com.zhuzr.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhuzr.common.RedisUtils;
import com.zhuzr.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteRegister {
    private static final RedisUtils redisUtils = new RedisUtils();

    public static void register(String interfaceName, URL url) throws JsonProcessingException {
        List<URL> list = redisUtils.getURLs(interfaceName);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);
        redisUtils.putURLs(interfaceName, list);
    }

    public static List<URL> get(String interfaceName) throws JsonProcessingException {
        return redisUtils.getURLs(interfaceName);
    }
}
