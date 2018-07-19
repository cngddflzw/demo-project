package com.zim.demo.rpcproxy.sidecar.core.heterogeneous2java;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Maps;
import com.zim.demo.rpcproxy.api.DelegateService;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.config.RegisterConfig;
import com.zim.demo.rpcproxy.sidecar.exception.RemoteInvokeException;
import java.util.Map;

/**
 * 用于声明异构语言需要引用的 Java 服务, 声明以后异构语言即可调用该 Java 服务
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Heterogeneous2DubboService implements DelegateService {

    private static final String[] PARAM_TYPE_TOKEN = new String[0];

    private final ReferenceConfig<GenericService> referenceConfig;
    private final Map<String, GenericService> serviceMap = Maps.newHashMap();

    private ResponseParser<Object> responseParser;

    public Heterogeneous2DubboService(RegisterConfig registerConfig,
            ResponseParser<Object> responseParser) {
        this.responseParser = responseParser;

        ApplicationConfig application = new ApplicationConfig(
                registerConfig.getApplication().getName());
        application.setOrganization(registerConfig.getApplication().getOrganization());

        RegistryConfig registry = new RegistryConfig(registerConfig.getRegistry().getProtocol());
        registry.setAddress(registerConfig.getRegistry().getAddress());
        registry.setPort(registerConfig.getRegistry().getPort());

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

        reference.setApplication(application);
        reference.setRegistry(registry);

        this.referenceConfig = reference;
    }

    @Override
    public void refer(ServiceInfo serviceInfo) {
        referenceConfig.setInterface(serviceInfo.name());
        referenceConfig.setVersion(serviceInfo.version());
        referenceConfig.setGroup(serviceInfo.group());
        serviceMap.put(serviceInfo.name(), referenceConfig.get());
    }

    @Override
    public Object invoke(Invocation invocation) {
        String serviceName = invocation.interfaceName();
        GenericService service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RemoteInvokeException(
                    String.format("service %s 不存在, 请先 refer 该 service", serviceName));
        }
        String methodName = invocation.methodName();
        Map<String, Object> params = invocation.params();
        Object result = service.$invoke(methodName, params.keySet().toArray(PARAM_TYPE_TOKEN),
                params.values().toArray());
        return responseParser.parse(result);
    }
}
