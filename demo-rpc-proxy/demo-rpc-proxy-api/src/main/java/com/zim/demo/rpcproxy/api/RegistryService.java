package com.zim.demo.rpcproxy.api;

/**
 * 注册服务
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface RegistryService {

    /**
     * 服务注册, 用于注册异构服务
     *
     * @param serviceInfo 服务信息
     */
    void register(ServiceInfo serviceInfo);

    /**
     * 服务下线, 用于下线异构服务
     *
     * @param serviceInfo 服务信息
     */
    void unregister(ServiceInfo serviceInfo);
}
