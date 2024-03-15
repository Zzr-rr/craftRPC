package com.zhuzr.rpc.common.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.ServiceAddress;
import com.zhuzr.rpc.common.registry.utils.ZkUtils;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceRegistry {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void registerService(String interfaceName, String version, ServiceAddress serviceAddress) {
        String path = ZkUtils.ZK_URL_SECOND_LEVEL_PATH + interfaceName + version;
        String value = ZkUtils.getPersistentNode(path);
        List<ServiceAddress> deserializedServiceAddresses = new ArrayList<>();
        try {
            if (value != null)
                deserializedServiceAddresses = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, ServiceAddress.class));
            if (!deserializedServiceAddresses.contains(serviceAddress)) deserializedServiceAddresses.add(serviceAddress);
            // 序列化
            value = mapper.writeValueAsString(deserializedServiceAddresses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ZkUtils.createPersistentNode(path, value);
    }
}
