package com.zim.demo.rpcproxy.sidecar.core;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationService;
import com.zim.demo.rpcproxy.api.ServiceManager;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import com.zim.demo.rpcproxy.sidecar.common.ServiceKeyGenerator;
import com.zim.demo.rpcproxy.sidecar.exception.Heterogeneous2JavaException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用于声明异构语言需要引用的 Java 服务, 声明以后异构语言即可调用该 Java 服务 这里最终的返回结果类型是 InvocationResult, 符合异构语言调用规范
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@Service
public class Heterogeneous2JavaService implements InvocationService {

    private static final String[] PARAM_TYPE_TOKEN = new String[0];

    @Resource
    private ServiceManager<GenericService> serviceManager;

    @Resource
    private ServiceKeyGenerator serviceKeyGenerator;

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) {
        String serviceKey = serviceKeyGenerator.generate(invocation);
        DefaultServiceInfo serviceInfo = new DefaultServiceInfo()
                .setServiceName(invocation.interfaceName())
                .setGroup(invocation.group())
                .setVersion(invocation.version());
        GenericService service = serviceManager.refer(serviceInfo);
        if (service == null) {
            throw new Heterogeneous2JavaException(
                    String.format("service %s 不存在, 请先 refer 该 service", serviceKey));
        }
        String methodName = invocation.methodName();
        Object result = service
                .$invoke(methodName, invocation.paramTypes().toArray(PARAM_TYPE_TOKEN),
                        invocation.paramVals().toArray());
        return result;
    }

}
