package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Maps;
import com.zim.demo.rpcproxy.api.ExportService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.common.ServiceKeyGenerator;
import com.zim.demo.rpcproxy.sidecar.config.ExportConfig;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于注册和下线 异构语言 服务, 注册以后即可对其他 Java 应用暴露该异构语言服务, 其他 Java 应用则可以调用该服务
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DubboExportService implements ExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboExportService.class);

    private final Map<String, ServiceConfig<GenericService>> serviceMap = Maps.newHashMap();
    private final ExportConfig exportConfig;
    private final GenericService genericService;
    private final ServiceKeyGenerator serviceKeyGenerator;

    public DubboExportService(ExportConfig exportConfig, GenericService genericService,
            ServiceKeyGenerator serviceKeyGenerator) {
        this.exportConfig = exportConfig;
        this.genericService = genericService;
        this.serviceKeyGenerator = serviceKeyGenerator;
    }

    @Override
    public void export(ServiceInfo serviceInfo) {
        String serviceKey = serviceKeyGenerator.generate(serviceInfo);

        synchronized (serviceKey.intern()) {
            ServiceConfig<GenericService> old = serviceMap.get(serviceKey);
            if (old != null) {
                old.unexport();
                serviceMap.remove(serviceKey);
            }
            ServiceConfig<GenericService> serviceConfig = initServiceConfig();

            serviceConfig.setInterface(serviceInfo.serviceName());
            serviceConfig.setVersion(serviceInfo.version());
            serviceConfig.setGroup(serviceInfo.group());
            serviceConfig.export();

            serviceMap.put(serviceKey, serviceConfig);

            LOGGER.info("service {} registered successfully", serviceKey);
        }
    }

    @Override
    public void unexport(ServiceInfo serviceInfo) {
        String serviceKey = serviceKeyGenerator.generate(serviceInfo);
        synchronized (serviceKey.intern()) {
            ServiceConfig<GenericService> serviceConfig = serviceMap.get(serviceKey);
            if (serviceConfig != null) {
                serviceConfig.unexport();
                serviceMap.remove(serviceKey);
            }
        }
    }

    private ServiceConfig<GenericService> initServiceConfig() {
        ApplicationConfig application = new ApplicationConfig(
                exportConfig.getApplication().getName());
        application.setOrganization(exportConfig.getApplication().getOrganization());

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(exportConfig.getProtocol().getName());
        protocol.setPort(exportConfig.getProtocol().getPort());

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(exportConfig.getRegistry().getProtocol());
        registry.setAddress(exportConfig.getRegistry().getAddress());
        registry.setPort(exportConfig.getRegistry().getPort());

        ServiceConfig<GenericService> serviceConfig = new ServiceConfig<>();

        serviceConfig.setApplication(application);
        serviceConfig.setProtocol(protocol);
        serviceConfig.setRegistry(registry);

        serviceConfig.setRef(genericService);

        return serviceConfig;
    }
}
