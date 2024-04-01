package com.zhuzr.rpc.server.utils;

import com.zhuzr.rpc.server.pojo.RpcProvider;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
    public static RpcProvider parseXML() {
        SAXReader saxReader = new SAXReader();
        RpcProvider rpcProvider = new RpcProvider();
        try {
            Document document = saxReader.read(XmlUtil.class.getClassLoader().getResourceAsStream("META-INF/craft.xml"));
            Element rootElement = document.getRootElement();

            String packagePath = rootElement.elementTextTrim("packagePath");
            String host = rootElement.elementTextTrim("host");
            int port = Integer.parseInt(rootElement.elementTextTrim("port") != null ? rootElement.elementTextTrim("port") : "0");
            String registration = rootElement.elementTextTrim("registration");
            String registrationAddress = rootElement.elementTextTrim("registration");
            if (packagePath == null || host == null || port == 0 || registrationAddress == null) return null;
            if (registration == null) registration = "zookeeper";

            rpcProvider.setPackagePath(packagePath);
            rpcProvider.setHost(host);
            rpcProvider.setPort(port);
            rpcProvider.setRegistration(registration);
            rpcProvider.setRegistrationAddress(registrationAddress);
        } catch (DocumentException e) {
            throw new RuntimeException();
        }
        return rpcProvider;
    }
}
