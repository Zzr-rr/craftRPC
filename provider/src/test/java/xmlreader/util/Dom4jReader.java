package xmlreader.util;

import com.zhuzr.rpc.server.pojo.RpcProvider;
import com.zhuzr.rpc.server.pojo.Service;
import com.zhuzr.rpc.server.pojo.Services;
import com.zhuzr.rpc.server.utils.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dom4jReader {
    public static void main(String[] args) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(XmlUtil.class.getClassLoader().getResourceAsStream("META-INF/craft.xml"));
        Element rootElement = document.getRootElement();
        RpcProvider rpcProvider = new RpcProvider();

        String packagePath = rootElement.elementTextTrim("packagePath");
        String host = rootElement.elementTextTrim("host");
        int port = Integer.parseInt(rootElement.elementTextTrim("port") != null ? rootElement.elementTextTrim("port") : "0");
        String registration = rootElement.elementTextTrim("registration");
        String registrationAddress = rootElement.elementTextTrim("registration");
        if (packagePath == null || host == null || port == 0 || registrationAddress == null) return;
        if (registration == null) registration = "zookeeper";

        rpcProvider.setPackagePath(packagePath);
        rpcProvider.setHost(host);
        rpcProvider.setPort(port);
        rpcProvider.setRegistration(registration);
        rpcProvider.setRegistrationAddress(registrationAddress);

        System.out.println(rpcProvider);
    }
}
