package com.zim.demo.rpcproxy.api.impl;

import com.google.common.collect.Maps;
import com.zim.demo.rpcproxy.api.Invocation;
import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultInvocation implements Invocation {

    private String interfaceName;

    private String methodName;

    private Map<String, Object> params;

    public String getInterfaceName() {
        return interfaceName;
    }

    public DefaultInvocation setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public DefaultInvocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public DefaultInvocation setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public DefaultInvocation addParam(String key, Object val) {
        if (params == null) {
            params = Maps.newHashMap();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public String interfaceName() {
        return interfaceName;
    }

    @Override
    public String methodName() {
        return methodName;
    }

    @Override
    public Map<String, Object> params() {
        return params;
    }
}
