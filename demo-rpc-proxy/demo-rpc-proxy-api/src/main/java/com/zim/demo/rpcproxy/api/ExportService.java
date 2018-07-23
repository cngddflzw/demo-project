package com.zim.demo.rpcproxy.api;

/**
 * 暴露服务
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface ExportService {

    /**
     * 服务暴露, 用于暴露异构服务
     *
     * TODO 1. 代理必须有一个机制, 用于探测服务是否在线, 还是说我们默认只要 proxy 在线, 后面的服务就在线
     * TODO 2. Sidecar 重启以后谁来帮忙重新注册服务
     * TODO 3. 考虑 heterogeneous 掉线, 重新上线等, 是否会影响服务稳定性
     * @param serviceInfo 服务信息
     */
    void export(ServiceInfo serviceInfo);

    /**
     * 服务下线, 用于下线异构服务
     *
     * @param serviceInfo 服务信息
     */
    void unexport(ServiceInfo serviceInfo);
}
