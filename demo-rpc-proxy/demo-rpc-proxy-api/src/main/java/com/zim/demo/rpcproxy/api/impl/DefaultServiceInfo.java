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
