package com.zhuzr.rpc.server.pojo;


public class Service {

    private String rawMethod;
    private Class implementation;
    private String host;
    private int port;

    public Service() {
    }

    public Service(String rawMethod, Class implementation, String host, int port) {
        this.rawMethod = rawMethod;
        this.implementation = implementation;
        this.host = host;
        this.port = port;
    }

    public String getRawMethod() {
        return rawMethod;
    }

    public void setRawMethod(String rawMethod) {
        this.rawMethod = rawMethod;
    }

    public Class getImplementation() {
        return implementation;
    }

    public void setImplementation(Class implementation) {
        this.implementation = implementation;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
