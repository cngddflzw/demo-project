package com.zim.demo.rpcproxy.api.impl;

import com.zim.demo.rpcproxy.api.ServiceInfo;
import javax.validation.constraints.NotNull;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultServiceInfo implements ServiceInfo {

    @NotNull
    private String name;

    @NotNull
    private String version;

    @NotNull
    private String group;

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

    public String getGroup() {
        return group;
    }

    public DefaultServiceInfo setGroup(String group) {
        this.group = group;
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
        return group;
    }
}
