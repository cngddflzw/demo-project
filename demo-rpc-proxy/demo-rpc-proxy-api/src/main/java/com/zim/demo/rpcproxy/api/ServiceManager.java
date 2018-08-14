package com.zim.demo.rpcproxy.api;

/**
 * @author zhenwei.liu
 * @since 2018-07-23
 */
public interface ServiceManager<Service> {

    /**
     * 暴露服务
     *
     * @param serviceInfo 服务信息
     */
    void export(ServiceInfo serviceInfo);

    /**
     * 下线服务
     *
     * @param serviceInfo 服务信息
     */
    void unexport(ServiceInfo serviceInfo);

    /**
     * 引用服务, 不存在则创建
     *
     * @param serviceInfo 服务信息
     * @return 服务引用
     */
    Service refer(ServiceInfo serviceInfo);


    /**
     * 获取 reference 信息
     *
     * @param key key
     * @return 引用的 reference 信息
     */
    ServiceInfo getReferenceInfo(String key);
}
