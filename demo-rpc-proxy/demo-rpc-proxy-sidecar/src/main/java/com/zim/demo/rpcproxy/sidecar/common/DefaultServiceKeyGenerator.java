package com.zim.demo.rpcproxy.sidecar.common;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import org.springframework.stereotype.Component;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Component
public class DefaultServiceKeyGenerator implements ServiceKeyGenerator {

    private static final String SEP = ":";

    @Override
    public String generate(String serviceName, String group, String version) {
        return serviceName + SEP +
                group + SEP +
                version;
    }

    @Override
    public String generate(ServiceInfo serviceInfo) {
        return generate(serviceInfo.serviceName(), serviceInfo.group(), serviceInfo.version());
    }

    @Override
    public String generate(Invocation invocation) {
        return invocation.interfaceName() + SEP +
                invocation.group() + SEP +
                invocation.version();
    }
}
