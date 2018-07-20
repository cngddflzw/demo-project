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
import com.zim.demo.rpcproxy.sidecar.common.ServiceKeyGenerator;
import com.zim.demo.rpcproxy.sidecar.config.RegisterConfig;
import com.zim.demo.rpcproxy.sidecar.exception.Heterogeneous2JavaException;
import java.util.Map;

/**
 * 用于声明异构语言需要引用的 Java 服务, 声明以后异构语言即可调用该 Java 服务 这里最终的返回结果类型是 InvocationResult, 符合异构语言调用规范
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Heterogeneous2DubboService implements InvocationService {

    private static final String[] PARAM_TYPE_TOKEN = new String[0];

    private final Map<String, GenericService> serviceMap = Maps.newHashMap();
    private final RegisterConfig registerConfig;
    private final ServiceKeyGenerator serviceKeyGenerator;
    private final ResponseParser<Object, InvocationResult> responseParser;

    public Heterogeneous2DubboService(RegisterConfig registerConfig,
            ServiceKeyGenerator serviceKeyGenerator,
            ResponseParser<Object, InvocationResult> responseParser) {
        this.registerConfig = registerConfig;
        this.serviceKeyGenerator = serviceKeyGenerator;
        this.responseParser = responseParser;
    }

    @Override
    public void refer(ServiceInfo serviceInfo) {
        String serviceName = serviceInfo.name();

        ReferenceConfig<GenericService> referenceConfig = initReferenceConfig();
        referenceConfig.setInterface(serviceName);
        referenceConfig.setVersion(serviceInfo.version());
        referenceConfig.setGroup(serviceInfo.group());

        String serviceKey = serviceKeyGenerator.generate(serviceInfo);

        synchronized (serviceKey.intern()) {
            if (!serviceMap.containsKey(serviceKey)) {
                serviceMap.put(serviceKey, referenceConfig.get());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) {
        String serviceKey = serviceKeyGenerator.generate(invocation);
        GenericService service = serviceMap.get(serviceKey);
        if (service == null) {
            throw new Heterogeneous2JavaException(
                    String.format("service %s 不存在, 请先 refer 该 service", serviceKey));
        }
        String methodName = invocation.methodName();
        Object result = service
                .$invoke(methodName, invocation.paramTypes().toArray(PARAM_TYPE_TOKEN),
                        invocation.paramVals().toArray());
        return responseParser.parse(result);
    }

    private ReferenceConfig<GenericService> initReferenceConfig() {
        ApplicationConfig application = new ApplicationConfig(
                registerConfig.getApplication().getName());
        application.setOrganization(registerConfig.getApplication().getOrganization());

        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(registerConfig.getRegistry().getProtocol());
        registry.setAddress(registerConfig.getRegistry().getAddress());
        registry.setPort(registerConfig.getRegistry().getPort());

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();

        referenceConfig.setApplication(application);
        referenceConfig.setRegistry(registry);
        referenceConfig.setGeneric(true);

        return referenceConfig;
    }
}
