package com.zim.demo.rpcproxy.sidecar.common;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.ServiceInfo;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public interface ServiceKeyGenerator {

    String generate(ServiceInfo serviceInfo);

    String generate(Invocation invocation);
}
