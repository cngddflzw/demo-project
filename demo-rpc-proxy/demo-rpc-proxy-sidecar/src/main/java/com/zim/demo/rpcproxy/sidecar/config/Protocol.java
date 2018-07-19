package com.zim.demo.rpcproxy.sidecar.config;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Protocol {

    private String name;
    private int port;

    public String getName() {
        return name;
    }

    public Protocol setName(String name) {
        this.name = name;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Protocol setPort(int port) {
        this.port = port;
        return this;
    }
}
