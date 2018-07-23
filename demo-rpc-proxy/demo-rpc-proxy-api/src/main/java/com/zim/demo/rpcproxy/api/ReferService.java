package com.zim.demo.rpcproxy.api;

/**
 * 服务泛化调用接口
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface ReferService {

    /**
     * <pre>
     * 服务发现, 用于异构服务调用其他服务
     * 例如对 Dubbo 服务来讲, 异构语言需要调用 Dubbo 服务, 需要先创建服务引用
     * 这样可以避免调用时零时创建服务引用影响调用性能
     * </pre>
     *
     * @param serviceInfo 服务信息
     */
    void refer(ServiceInfo serviceInfo);

    /**
     * 调用服务
     *
     * @param invocation 泛化调用参数
     * @return 调用结果
     */
    Object invoke(Invocation invocation);
}
