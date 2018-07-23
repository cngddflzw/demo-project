package com.zim.demo.rpcproxy.sidecar.core;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Maps;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.api.ServiceManager;
import com.zim.demo.rpcproxy.sidecar.common.ServiceKeyGenerator;
import com.zim.demo.rpcproxy.sidecar.config.ExportConfig;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-07-23
 */
@Service
public class DubboServiceManager implements ServiceManager<GenericService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboServiceManager.class);

    private final Map<String, ServiceConfig<GenericService>> serviceMap = Maps.newHashMap();
    private final Map<String, GenericService> referenceMap = Maps.newHashMap();

    @Resource
    private ExportConfig exportConfig;

    @Resource
    private GenericService genericService;

    @Resource
    private ServiceKeyGenerator serviceKeyGenerator;

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

    @Override
    public GenericService refer(ServiceInfo serviceInfo) {

        String serviceKey = serviceKeyGenerator.generate(serviceInfo);

        synchronized (serviceKey.intern()) {
            GenericService genericService = referenceMap.get(serviceKey);
            if (genericService == null) {
                String serviceName = serviceInfo.serviceName();

                ReferenceConfig<GenericService> referenceConfig = initReferenceConfig();
                referenceConfig.setInterface(serviceName);
                referenceConfig.setVersion(serviceInfo.version());
                referenceConfig.setGroup(serviceInfo.group());
                genericService = referenceConfig.get();
                referenceMap.put(serviceKey, genericService);

                LOGGER.info("service {} refer successfully", serviceKey);
            }
            return genericService;
        }
    }

    private ReferenceConfig<GenericService> initReferenceConfig() {
        ApplicationConfig application = new ApplicationConfig(
                exportConfig.getApplication().getName());
        application.setOrganization(exportConfig.getApplication().getOrganization());

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(exportConfig.getRegistry().getProtocol());
        registry.setAddress(exportConfig.getRegistry().getAddress());
        registry.setPort(exportConfig.getRegistry().getPort());

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();

        referenceConfig.setApplication(application);
        referenceConfig.setRegistry(registry);
        referenceConfig.setGeneric(true);

        return referenceConfig;
    }
}
