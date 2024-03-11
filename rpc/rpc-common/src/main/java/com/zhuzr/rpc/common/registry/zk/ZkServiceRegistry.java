package com.zhuzr.rpc.common.registry.zk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.registry.zk.utils.ZkUtils;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceRegistry {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void registerService(String interfaceName, URL url) {
        String path = "/urls/" + interfaceName;
        String value = ZkUtils.getValue(path);
        List<URL> deserializedUrls = new ArrayList<>();
        try {
            if (value != null)
                deserializedUrls = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
            if (!deserializedUrls.contains(url)) deserializedUrls.add(url);
            value = mapper.writeValueAsString(deserializedUrls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ZkUtils.createPersistentNode(path, value);
    }
}
