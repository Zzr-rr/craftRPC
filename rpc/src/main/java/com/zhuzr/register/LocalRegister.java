package com.zhuzr.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地注册工具
 */
public class LocalRegister {
    private static Map<String, Class> map = new HashMap<>();

    public static void register(String interFaceName, String version, Class implClass) {
        map.put(interFaceName + version, implClass);
    }

    public static Class get(String interfaceName, String version) {
        return map.get(interfaceName + version);
    }
}
