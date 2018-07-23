package com.zim.demo.rpcproxy.sidecar.common;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.ServiceInfo;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class DefaultServiceKeyGenerator implements ServiceKeyGenerator {

    public static final ServiceKeyGenerator INSTANCE = new DefaultServiceKeyGenerator();

    private static final String SEP = ":";

    @Override
    public String generate(ServiceInfo serviceInfo) {
        return serviceInfo.serviceName() + SEP +
                serviceInfo.group() + SEP +
                serviceInfo.version();
    }

    @Override
    public String generate(Invocation invocation) {
        return invocation.interfaceName() + SEP +
                invocation.group() + SEP +
                invocation.version();
    }
}
