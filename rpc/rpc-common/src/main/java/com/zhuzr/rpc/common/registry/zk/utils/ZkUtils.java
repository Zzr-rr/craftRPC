package com.zhuzr.rpc.common.registry.zk.utils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.awt.*;

public final class ZkUtils {
    private static final String ZK_REGISTER_ROOT_PATH = "/craft-rpc";
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "123.56.138.30:2181";
    private static final int DEFAULT_SESSION_TIME_OUT = 60000 * 30;
    private static final int DEFAULT_CONNECTION_TIME_OUT = 60000;
    private static final ZkClient zkClient = new ZkClient(DEFAULT_ZOOKEEPER_ADDRESS, DEFAULT_CONNECTION_TIME_OUT, DEFAULT_SESSION_TIME_OUT, new SerializableSerializer());

    public static void createPersistentNode(String path, String value) {
        String node = ZK_REGISTER_ROOT_PATH + path;
        String parentNode = node.substring(0, node.lastIndexOf("/"));
        if (!zkClient.exists(parentNode)) {
            zkClient.createPersistent(parentNode, true);
        }
        if (zkClient.exists(node)) {
            zkClient.writeData(node, value);
        } else zkClient.create(node, value, CreateMode.EPHEMERAL);
    }

    public static String getValue(String path) {
        String node = ZK_REGISTER_ROOT_PATH + path;
        if (!zkClient.exists(node)) {
            return null;
        }
        return zkClient.readData(node);
    }
}
