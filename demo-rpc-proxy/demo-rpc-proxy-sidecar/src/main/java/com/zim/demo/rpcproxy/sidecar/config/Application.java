package com.zim.demo.rpcproxy.sidecar.config;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Application {

    private String name;
    private String organization;

    public String getName() {
        return name;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public Application setOrganization(String organization) {
        this.organization = organization;
        return this;
    }
}
