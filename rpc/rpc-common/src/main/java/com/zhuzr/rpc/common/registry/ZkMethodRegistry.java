package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;


public class ZkMethodRegistry {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void registerMethod(String interfaceName, String version, Class implClass) {
        String path = ZkUtils.ZK_METHOD_SECOND_LEVEL_PATH + interfaceName + version;
        String value;
        try {
            value = mapper.writeValueAsString(implClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ZkUtils.createPersistentNode(path, value);
    }
}
