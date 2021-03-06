package com.zim.demo.rpcproxy.api;

import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface ServiceInfo {

    String serviceName();

    String version();

    String group();

    Map<String, ? extends MethodInfo> methodInfoMap();
}
