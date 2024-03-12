package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceRegistry {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void registerService(String interfaceName, String version, URL url) {
        String path = ZkUtils.ZK_URL_SECOND_LEVEL_PATH + interfaceName + version;
        String value = ZkUtils.getPersistentNode(path);
        List<URL> deserializedUrls = new ArrayList<>();
        try {
            if (value != null)
                deserializedUrls = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
            if (!deserializedUrls.contains(url)) deserializedUrls.add(url);
            // 序列化
            value = mapper.writeValueAsString(deserializedUrls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ZkUtils.createPersistentNode(path, value);
    }
}
