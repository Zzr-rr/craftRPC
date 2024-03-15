package com.zhuzr.rpc.common.registry.utils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public final class ZkUtils {
    private static final String ZK_REGISTER_ROOT_PATH = "/craft-rpc";
    public static final String ZK_URL_SECOND_LEVEL_PATH = "/urls/";
    public static final String ZK_METHOD_SECOND_LEVEL_PATH = "/services/";
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "123.56.138.30:2181";
    private static final int DEFAULT_SESSION_TIME_OUT = 60000 * 30;
    private static final int DEFAULT_CONNECTION_TIME_OUT = 60000;
    private static final ZkClient zkClient = new ZkClient(DEFAULT_ZOOKEEPER_ADDRESS, DEFAULT_CONNECTION_TIME_OUT, DEFAULT_SESSION_TIME_OUT, new SerializableSerializer());

    /**
     * 创建节点
     *
     * @param path:  节点创建路径
     * @param value: 序列化后的列表对象
     */
    public static void createPersistentNode(String path, String value) {
        String node = ZK_REGISTER_ROOT_PATH + path;
        String parentNode = node.substring(0, node.lastIndexOf("/"));
        if (!zkClient.exists(parentNode)) {
            zkClient.createPersistent(parentNode, true);
        }
        if (zkClient.exists(node)) {
            zkClient.writeData(node, value);
        } else zkClient.create(node, value, CreateMode.PERSISTENT);
    }

    /**
     * 获取指定路径下的节点
     *
     * @param path: 路径
     * @return: 序列化后的列表对象
     */
    public static String getPersistentNode(String path) {
        String node = ZK_REGISTER_ROOT_PATH + path;
        if (!zkClient.exists(node)) {
            return null;
        }
        return zkClient.readData(node);
    }

    /**
     * 删除指定路径下持久层节点
     *
     * @param path: 路径
     */
    public static void deleteAllPersistentNode(String path) {
        String node = ZK_REGISTER_ROOT_PATH + path;
        if (!zkClient.exists(node)) {
            return;
        }
        zkClient.deleteRecursive(node);
    }

}
