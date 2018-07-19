package com.zim.demo.rpcproxy.sidecar.config;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Registry {

    private String protocol;
    private String address;
    private int port;

    public String getProtocol() {
        return protocol;
    }

    public Registry setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Registry setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Registry setPort(int port) {
        this.port = port;
        return this;
    }
}
