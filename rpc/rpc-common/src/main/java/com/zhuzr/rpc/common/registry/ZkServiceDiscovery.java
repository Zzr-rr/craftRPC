package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceDiscovery {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<URL> lookupService(String interfaceName, String version) {
        String path = ZkUtils.ZK_URL_SECOND_LEVEL_PATH + interfaceName + version;
        String value = ZkUtils.getPersistentNode(path);
        List<URL> deserializedUrls = new ArrayList<>();
        try {
            // 反序列化
            if (value != null)
                deserializedUrls = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deserializedUrls;
    }
}
