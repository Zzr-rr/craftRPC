package com.zhuzr.rpc.server.protocol;


import com.zhuzr.rpc.common.pojo.Invocation;
import com.zhuzr.rpc.common.registry.ZkMethodDiscovery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 处理接口请求
 */

public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        // 处理请求 --> 接口、方法、方法参数
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();

            // 读取默认的版本号
            // Class classImpl = LocalRegister.get(interfaceName, "1.0");
            Class classImpl = ZkMethodDiscovery.lookupMethod(interfaceName, "1.0");
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

            // 将执行的结果写进Resp
            IOUtils.write(result, resp.getOutputStream());
        } catch (IOException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException |
                 IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
