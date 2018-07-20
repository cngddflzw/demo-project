package com.zim.demo.rpcproxy.sidecar.core;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.core.heterogeneous2java.Heterogeneous2JavaService;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DubboSidecarService implements SidecarService {

    private RegistryService registryService;

    private Heterogeneous2JavaService heterogeneous2JavaService;

    public DubboSidecarService(
            RegistryService registryService,
            Heterogeneous2JavaService heterogeneous2JavaService) {
        this.registryService = registryService;
        this.heterogeneous2JavaService = heterogeneous2JavaService;
    }

    @Override
    public void refer(ServiceInfo serviceInfo) {
        heterogeneous2JavaService.refer(serviceInfo);
    }

    @Override
    public Object invoke(Invocation invocation) {
        return heterogeneous2JavaService.invoke(invocation);
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
