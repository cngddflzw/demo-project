package com.zim.demo.rpcproxy.sidecar.core;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.core.heterogeneous2java.Heterogeneous2DubboService;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DubboSidecarService implements SidecarService {

    private RegistryService registryService;

    private Heterogeneous2DubboService heterogeneous2DubboService;

    public DubboSidecarService(
            RegistryService registryService,
            Heterogeneous2DubboService heterogeneous2DubboService) {
        this.registryService = registryService;
        this.heterogeneous2DubboService = heterogeneous2DubboService;
    }

    @Override
    public void refer(ServiceInfo serviceInfo) {
        heterogeneous2DubboService.refer(serviceInfo);
    }

    @Override
    public Object invoke(Invocation invocation) {
        return heterogeneous2DubboService.invoke(invocation);
    }

    @Override
    public void register(ServiceInfo serviceInfo) {
        registryService.register(serviceInfo);
    }

    @Override
    public void unregister(ServiceInfo serviceInfo) {
        registryService.unregister(serviceInfo);
    }
}
