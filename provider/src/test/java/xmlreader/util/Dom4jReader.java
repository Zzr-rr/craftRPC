package xmlreader.util;

import com.zhuzr.rpc.server.pojo.Service;
import com.zhuzr.rpc.server.pojo.Services;
import com.zhuzr.rpc.server.utils.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dom4jReader {
    public static void main(String[] args) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(XmlUtil.class.getClassLoader().getResourceAsStream("META-INF/craft.xml"));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements("service");

        List<com.zhuzr.rpc.server.pojo.Service> services = new CopyOnWriteArrayList<>();
        for (Element element : elements) {
            String rawMethod = element.elementTextTrim("rawMethod");
            Class implementation = element.elementTextTrim("implementation").getClass();
            String host = element.elementTextTrim("host");
            int port = Integer.parseInt(element.elementTextTrim("port"));
            com.zhuzr.rpc.server.pojo.Service service = new Service(rawMethod, implementation, host, port);
            services.add(service);
        }

        Services resultServices = new Services(services);
    }
}
