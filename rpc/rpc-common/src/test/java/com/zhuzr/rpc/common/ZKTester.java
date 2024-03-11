package com.zhuzr.rpc.common;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZKTester {
    @Before
    public void before() {
        // 参数1  --> zk server 服务ip地址:端口号
        // 参数2 -->  会话超时时间
        // 参数3  --> 连接超时时间
        // 参数4  --> 序列化方式
        zkClient = new ZkClient("123.56.138.30:2181", 60000 * 30, 60000, new SerializableSerializer());
    }

    private ZkClient zkClient;

    @Test
    public void test() {
        zkClient.create("/app1", "xue", CreateMode.PERSISTENT);
        //创建持久节点
        zkClient.create("/app2", "xue", CreateMode.PERSISTENT);
        //创建持久顺序节点
        zkClient.create("/app3", "yue", CreateMode.PERSISTENT_SEQUENTIAL);
        //创建临时节点
        zkClient.create("/app4", "qing", CreateMode.EPHEMERAL);
        //创建临时顺序节点
        zkClient.create("/app5", "haha", CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Test
    public void test2() {
        // 查看某个节点数据，需要保证创建和获取的数据序列化方式相同，如果是在linux上创建的节点那么在Java上获取就会出现序列化不匹配的异常
        Object o = zkClient.readData("/app2");
        System.out.println(o);
        // 查看节点状态
        Stat stat = new Stat();
        System.out.println(o);
        // 查看节点创建时间
        System.out.println(stat.getCtime());
        // 查看版本
        System.out.println(stat.getCversion());
        // 查看id
        System.out.println(stat.getCzxid());
    }

    @Test
    public void test3() {
        zkClient.writeData("/app1", "xue");
    }

    @Test
    public void test4() {
        List<String> children = zkClient.getChildren("/");
        for (String c : children) {
            System.out.println(c);
        }
    }

    @Test
    public void test5() throws IOException {

        zkClient.subscribeDataChanges("/app1", new IZkDataListener() {
            //当前节点数据变化时触发
            @Override
            public void handleDataChange(String dataPath, Object o) throws Exception {
                System.out.println("当前节点路径" + dataPath);
                System.out.println("当前节点信息" + o);
            }

            //当前节点删除时触发
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("当前节点路径" + dataPath);
            }
        });
        //阻塞当前监听
        System.in.read();
    }


    @After
    public void after() {
        zkClient.close();
    }

}
