package annotation;

import com.zhuzr.rpc.common.annotations.RpcService;

import java.util.*;

public class TestRpc {
    public static void main(String[] args) {
        String packageName = "com.zhuzr.service.impl";
        Set<Class<?>> clazs = AnnotationScanner.getClassesWithAnnotations(packageName, RpcService.class);
        for(Class<?> clz: clazs){
            System.out.println(clz.getName());
            Class<?>[] clzInterfaces = clz.getInterfaces();
            System.out.println(clzInterfaces[0].getName());
            System.out.println(clz.getAnnotation(RpcService.class).version());
        }
    }
}

