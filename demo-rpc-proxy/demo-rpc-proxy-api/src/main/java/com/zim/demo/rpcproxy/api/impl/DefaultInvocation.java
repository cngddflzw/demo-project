package com.zim.demo.rpcproxy.api.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.zim.demo.rpcproxy.api.Invocation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DefaultInvocation implements Invocation {

    private String interfaceName;

    private String methodName;

    private Map<String, Object> params;

    private DefaultInvocation() {
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {

        private String interfaceName;

        private String methodName;

        private final Map<String, Object> params = new HashMap<>();

        public Builder setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder addParam(String paramType, Object paramVal) {
            params.put(paramType, paramVal);
            return this;
        }

        public DefaultInvocation build() {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(interfaceName),
                    "interfaceName must not be empty");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(methodName),
                    "methodName must not be empty");

            DefaultInvocation invocation = new DefaultInvocation();
            invocation.interfaceName = this.interfaceName;
            invocation.methodName = this.methodName;
            invocation.params = this.params;

            return invocation;
        }
    }
}
