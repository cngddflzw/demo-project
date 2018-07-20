package com.zim.demo.rpcproxy.api.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zim.demo.rpcproxy.api.Invocation;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultInvocation implements Invocation {

    @NotNull
    private String interfaceName;

    @NotNull
    private String group;

    @NotNull
    private String version;

    @NotNull
    private String methodName;

    private List<String> paramTypes;
    private List<Object> paramVals;

    public String getInterfaceName() {
        return interfaceName;
    }

    public DefaultInvocation setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public DefaultInvocation setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public DefaultInvocation setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public DefaultInvocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public DefaultInvocation setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public List<Object> getParamVals() {
        return paramVals;
    }

    public DefaultInvocation setParamVals(List<Object> paramVals) {
        this.paramVals = paramVals;
        return this;
    }

    public void addParam(String type, Object val) {

        if (paramTypes == null) {
            paramTypes = Lists.newArrayList();
        }

        if (paramVals == null) {
            paramVals = Lists.newArrayList();
        }

        Preconditions.checkState(paramTypes.size() == paramVals.size(),
                "paramTypes.size() must equal to paramVals.size()");

        paramTypes.add(type);
        paramVals.add(val);
    }

    @Override
    public String interfaceName() {
        return interfaceName;
    }

    @Override
    public String group() {
        return group;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String methodName() {
        return methodName;
    }

    @Override
    public List<String> paramTypes() {
        return paramTypes;
    }

    @Override
    public List<Object> paramVals() {
        return paramVals;
    }
}
