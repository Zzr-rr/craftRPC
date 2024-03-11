package com.zhuzr.rpc.common.registry.zk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuzr.rpc.common.pojo.URL;
import com.zhuzr.rpc.common.registry.zk.utils.ZkUtils;

import java.util.ArrayList;
import java.util.List;

public class ZkServiceDiscovery {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<URL> lookupService(String interfaceName) {
        String path = "/urls/" + interfaceName;
        String value = ZkUtils.getValue(path);
        List<URL> deserializedUrls = new ArrayList<>();
        try {
            if (value != null)
                // deserializedUrls = mapper.readValue(value, new TypeReference<List<URL>>() {});
                deserializedUrls = mapper.readValue(value, mapper.getTypeFactory().constructCollectionType(List.class, URL.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deserializedUrls;
    }
}
