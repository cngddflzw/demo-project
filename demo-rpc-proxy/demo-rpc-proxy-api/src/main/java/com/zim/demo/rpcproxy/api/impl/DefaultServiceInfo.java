package com.zim.demo.rpcproxy.api.impl;

import com.zim.demo.rpcproxy.api.MethodInfo;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultServiceInfo implements ServiceInfo {

    @NotNull
    private String serviceName;

    @NotNull
    private String version;

    @NotNull
    private String group;

    private Map<String, DefaultMethodInfo> methodInfoMap;

    public String getServiceName() {
        return serviceName;
    }

    public DefaultServiceInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public Map<String, DefaultMethodInfo> getMethodInfoMap() {
        return methodInfoMap;
    }

    public DefaultServiceInfo setMethodInfoMap(
            Map<String, DefaultMethodInfo> methodInfoMap) {
        this.methodInfoMap = methodInfoMap;
        return this;
    }

    @Override
    public String serviceName() {
        return serviceName;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String group() {
        return group;
    }

    @Override
    public Map<String, DefaultMethodInfo> methodInfoMap() {
        return methodInfoMap;
    }


}
