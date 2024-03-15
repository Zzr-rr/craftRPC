package com.zhuzr.rpc.common.pojo;

/**
 * URL类
 */
public class ServiceAddress {
    private String hostname;
    private int port;

    public ServiceAddress() {
    }

    public ServiceAddress(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
