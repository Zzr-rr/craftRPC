package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;

public class ZkMethodDiscovery {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Class lookupMethod(String interfaceName, String version) {
        String path = ZkUtils.ZK_METHOD_SECOND_LEVEL_PATH + interfaceName + version;
        String value = ZkUtils.getPersistentNode(path);
        Class deserializedClass = null;
        try {
            // 反序列化
            if (value != null)
                deserializedClass = mapper.readValue(value, Class.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deserializedClass;
    }
}
