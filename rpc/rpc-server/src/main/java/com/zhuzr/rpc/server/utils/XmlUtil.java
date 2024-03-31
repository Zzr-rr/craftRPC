package com.zhuzr.rpc.server.utils;

import com.zhuzr.rpc.server.pojo.Service;
import com.zhuzr.rpc.server.pojo.Services;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XmlUtil {
    public static Services parseXML() throws DocumentException {

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(XmlUtil.class.getClassLoader().getResourceAsStream("META-INF/craft.xml"));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements("service");
        List<Service> services = new CopyOnWriteArrayList<>();
        try {
            for (Element element : elements) {
                String rawMethod = element.elementTextTrim("rawMethod");
                Class implementation = Class.forName(element.elementTextTrim("implementation"));
                String host = element.elementTextTrim("host");
                int port = Integer.parseInt(element.elementTextTrim("port"));
                Service service = new Service(rawMethod, implementation, host, port);
                services.add(service);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Services(services);
    }
}
