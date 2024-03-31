package com.zhuzr.rpc.server.proxy;

import com.zhuzr.rpc.common.pojo.ServiceAddress;
import com.zhuzr.rpc.common.registry.ZkMethodRegistry;
import com.zhuzr.rpc.common.registry.ZkServiceRegistry;
import com.zhuzr.rpc.server.RpcServer;
import com.zhuzr.rpc.server.pojo.Service;
import com.zhuzr.rpc.server.pojo.Services;
import com.zhuzr.rpc.server.utils.XmlUtil;
import org.dom4j.DocumentException;

public class RpcServerInitializer {
    public RpcServerInitializer() {

    }
    public void start() {
        try {
            Services services = XmlUtil.parseXML();
            for (Service service : services.getServices()) {
                RpcServer rpcServer = new RpcServer();
                ServiceAddress serviceAddress = new ServiceAddress(service.getHost(), service.getPort());
                ZkServiceRegistry.registerService(service.getRawMethod(), "1.0", serviceAddress);
                ZkMethodRegistry.registerMethod(service.getRawMethod(), "1.0", service.getImplementation());
                rpcServer.start(service.getHost(), service.getPort());
            }
        } catch (DocumentException ignored) {

        }
    }
}
