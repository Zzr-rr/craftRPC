package com.zhuzr.rpc.server.proxy;

import com.zhuzr.rpc.common.annotations.RpcService;
import com.zhuzr.rpc.common.pojo.ServiceAddress;
import com.zhuzr.rpc.common.registry.ZkMethodRegistry;
import com.zhuzr.rpc.common.registry.ZkServiceRegistry;
import com.zhuzr.rpc.common.utils.AnnotationScanner;
import com.zhuzr.rpc.server.RpcServer;
import com.zhuzr.rpc.server.pojo.RpcProvider;
import com.zhuzr.rpc.server.utils.XmlUtil;

import java.util.Set;
import java.util.logging.Logger;

public class RpcServerInitializer {
    private static Logger logger = Logger.getLogger("RpcServerInitializer");

    public RpcServerInitializer() {

    }

    /**
     * server start the service
     * this method will do such steps:
     * 1. parse the xml
     * 2. create the rpcServer
     * 3. registry the service
     * 4. start the server
     */
    public void start() {
        RpcProvider rpcProvider = XmlUtil.parseXML();
        if (rpcProvider == null) {
            logger.info("server started failed");
            return;
        }
        RpcServer rpcServer = new RpcServer();
        ServiceAddress serviceAddress = new ServiceAddress(rpcProvider.getHost(), rpcProvider.getPort());
        try{
            serviceRegistry(serviceAddress, rpcProvider);
        } catch (ClassNotFoundException e){
            throw new RuntimeException();
        }
        rpcServer.start(rpcProvider.getHost(), rpcProvider.getPort());
    }

    void serviceRegistry(ServiceAddress serviceAddress, RpcProvider rpcProvider) throws ClassNotFoundException {
        Set<Class<?>> classes = AnnotationScanner.getClassesWithAnnotations(rpcProvider.getPackagePath(), RpcService.class);
        for(Class<?> clz: classes){
            try{
                Class implementation = Class.forName(clz.getName());
                Class<?>[] clzInterfaces = clz.getInterfaces();
                String version = clz.getAnnotation(RpcService.class).version();
                for(Class<?> clzInterface: clzInterfaces){
                    ZkServiceRegistry.registerService(clzInterface.getName(), version, serviceAddress);
                    ZkMethodRegistry.registerMethod(clzInterface.getName(), version, implementation);
                }
            } catch (ClassNotFoundException e){
                throw new RuntimeException();
            }
        }
//        ZkServiceRegistry.registerService(clazzs.getClass().getIn);
    }
}
