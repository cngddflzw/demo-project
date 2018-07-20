package com.zim.demo.rpcproxy.api.impl;

import com.zim.demo.rpcproxy.api.ServiceInfo;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultServiceInfo implements ServiceInfo {

    private String name;
    private String version;
    private String gorup;

    public String getName() {
        return name;
    }

    public DefaultServiceInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public DefaultServiceInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getGorup() {
        return gorup;
    }

    public DefaultServiceInfo setGorup(String gorup) {
        this.gorup = gorup;
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String group() {
        return group();
    }
}
