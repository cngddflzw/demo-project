package com.zim.demo.rpcproxy.sidecar.core.heterogeneous2java;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Maps;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.InvocationService;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.config.RegisterConfig;
import com.zim.demo.rpcproxy.sidecar.exception.Heterogeneous2JavaException;
import java.util.Map;

/**
 * 用于声明异构语言需要引用的 Java 服务, 声明以后异构语言即可调用该 Java 服务
 * 这里最终的返回结果类型是 InvocationResult, 符合异构语言调用规范
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Heterogeneous2DubboService implements InvocationService {

    private static final String[] PARAM_TYPE_TOKEN = new String[0];

    private final ReferenceConfig<GenericService> referenceConfig;
    private final Map<String, GenericService> serviceMap = Maps.newHashMap();

    private ResponseParser<Map<String, Object>, InvocationResult> responseParser;

    public Heterogeneous2DubboService(RegisterConfig registerConfig,
            ResponseParser<Map<String, Object>, InvocationResult> responseParser) {
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
    public synchronized void refer(ServiceInfo serviceInfo) {
        String serviceName = serviceInfo.name();
        referenceConfig.setInterface(serviceName);
        referenceConfig.setVersion(serviceInfo.version());
        referenceConfig.setGroup(serviceInfo.group());
        if (!serviceMap.containsKey(serviceName)) {
            serviceMap.put(serviceName, referenceConfig.get());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) {
        String serviceName = invocation.interfaceName();
        GenericService service = serviceMap.get(serviceName);
        if (service == null) {
            throw new Heterogeneous2JavaException(
                    String.format("service %s 不存在, 请先 refer 该 service", serviceName));
        }
        String methodName = invocation.methodName();
        Map<String, Object> params = invocation.params();
        Map<String, Object> result = (Map<String, Object>) service
                .$invoke(methodName, params.keySet().toArray(PARAM_TYPE_TOKEN),
                        params.values().toArray());
        return responseParser.parse(result);
    }
}
