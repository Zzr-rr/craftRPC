package xmlreader.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class XmlFinder {
    public static void main(String[] args) {
        // 使用 try-with-resources 语句确保 InputStream 被正确关闭
        try (InputStream inputStream = XmlFinder.class.getClassLoader().getResourceAsStream("META-INF/craft.xml")) {
            if (inputStream == null) {
                System.out.println("未找到文件");
                return;
            }
            byte[] bytebuffer = new byte[1024];
            int bytesRead;
            StringBuilder content = new StringBuilder();

            // 读取数据并处理
            while ((bytesRead = inputStream.read(bytebuffer)) != -1) {
                // 将读取的字节转换为字符串
                content.append(new String(bytebuffer, 0, bytesRead, StandardCharsets.UTF_8));
            }

            // 打印文件内容
            System.out.println(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
