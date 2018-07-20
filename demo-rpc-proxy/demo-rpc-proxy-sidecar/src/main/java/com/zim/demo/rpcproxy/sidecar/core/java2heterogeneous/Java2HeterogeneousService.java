package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
import com.zim.demo.rpcproxy.sidecar.common.Serializer;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;

/**
 * 用于 Java 服务反向调用异构语言服务的泛化调用 这里的最终返回结果类型是 Map<String, Object> (符合 dubbo 泛化调用规范)
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Java2HeterogeneousService implements GenericService {

    private Serializer<String> serializer;
    private RequestSender<InvocationResult> requestSender;

    public Java2HeterogeneousService(
            Serializer<String> serializer,
            RequestSender<InvocationResult> requestSender) {
        this.serializer = serializer;
        this.requestSender = requestSender;
    }

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {

        URL url = RpcContext.getContext().getUrl();
        String interfaceName = url.getServiceInterface();
        String group = url.getParameter("group");
        String version = url.getParameter("version");

        DefaultInvocation invocation = new DefaultInvocation()
                .setInterfaceName(interfaceName)
                .setGroup(group)
                .setVersion(version)
                .setMethodName(method);

        for (int i = 0; i < parameterTypes.length; i++) {
            invocation.addParam(parameterTypes[i], args[i]);
        }

        String request = serializer.serialize(invocation);
        InvocationResult result = requestSender.send(request);

        if (result.code() == 0) {
            // 按照泛化调用协议, 这里的 json 串应该转为 map
            // 这样才能兼容以后异构语言服务变为 java 服务
            return result.data();
        } else {
            throw new Java2HeterogeneousException(
                    String.format(
                            "invoke heterogeneous service %s.%s() error code: %s, message: %s",
                            interfaceName, method, result.code(), result.message()));
        }
    }
}
