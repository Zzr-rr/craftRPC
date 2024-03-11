package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.utils.RedisUtils;


import java.util.ArrayList;
import java.util.List;

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
