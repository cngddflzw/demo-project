package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.config.RegisterConfig;

/**
 * 用于注册和下线 异构语言 服务, 注册以后即可对其他 Java 应用暴露该异构语言服务,
 * 其他 Java 应用则可以调用该服务
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DubboRegistryService implements RegistryService {

    private final ServiceConfig<GenericService> serviceConfig;

    public DubboRegistryService(RegisterConfig registerConfig, GenericService genericService) {

        ApplicationConfig application = new ApplicationConfig(
                registerConfig.getApplication().getName());
        application.setOrganization(registerConfig.getApplication().getOrganization());

        ProtocolConfig protocol = new ProtocolConfig(registerConfig.getProtocol().getName());
        protocol.setPort(registerConfig.getProtocol().getPort());

        RegistryConfig registry = new RegistryConfig(registerConfig.getRegistry().getProtocol());
        registry.setAddress(registerConfig.getRegistry().getAddress());
        registry.setPort(registerConfig.getRegistry().getPort());

        ServiceConfig<GenericService> service = new ServiceConfig<>();

        service.setApplication(application);
        service.setProtocol(protocol);
        service.setRegistry(registry);

        service.setRef(genericService);

        this.serviceConfig = service;
    }

    @Override
    public synchronized void register(ServiceInfo serviceInfo) {
        serviceConfig.setInterface(serviceInfo.name());
        serviceConfig.setVersion(serviceInfo.version());
        serviceConfig.setGroup(serviceInfo.group());
        serviceConfig.export();
    }

    @Override
    public synchronized void unregister(ServiceInfo serviceInfo) {
        serviceConfig.setInterface(serviceInfo.name());
        serviceConfig.setVersion(serviceInfo.version());
        serviceConfig.setGroup(serviceInfo.group());
        serviceConfig.unexport();
    }
}
