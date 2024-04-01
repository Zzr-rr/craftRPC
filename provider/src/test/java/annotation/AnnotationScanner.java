package annotation;

import com.zhuzr.rpc.common.annotations.RpcService;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AnnotationScanner {
    public static Set<Class<?>> getClasses(String packagePath) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packagePath) // 指定扫描路径
                .setScanners(new SubTypesScanner(false))// 添加方法参数扫描工具，可以根据需要向该方法传入多个扫描工具
        );
        return reflections.getSubTypesOf(Object.class);
    }

    public static Set<Class<?>> getClassesWithAnnotations(String packageName, Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = getClasses(packageName);
        Set<Class<?>> classesWithAnnotations = new HashSet<>();
        for (Class<?> cls : classes) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classesWithAnnotations.add(cls);
            }
        }
        return classesWithAnnotations;
    }
}
