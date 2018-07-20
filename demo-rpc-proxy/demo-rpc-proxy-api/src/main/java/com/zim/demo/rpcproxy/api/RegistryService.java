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
     * // TODO 代理必须有一个机制, 用于探测服务是否在线, 还是说我们默认只要 proxy 在线, 后面的服务就在线
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
