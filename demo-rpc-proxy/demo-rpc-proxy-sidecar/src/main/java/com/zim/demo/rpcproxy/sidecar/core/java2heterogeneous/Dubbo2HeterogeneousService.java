package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation.Builder;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.common.Serializer;

/**
 * 用于 Java 服务反向调用异构语言服务的泛化调用
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Dubbo2HeterogeneousService implements GenericService {

    private Serializer<String> serializer;
    private ResponseParser<Object> responseParser;
    private RequestSender<String, Object> requestSender;

    public Dubbo2HeterogeneousService(
            Serializer<String> serializer,
            ResponseParser<Object> responseParser,
            RequestSender<String, Object> requestSender) {
        this.serializer = serializer;
        this.responseParser = responseParser;
        this.requestSender = requestSender;
    }

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {

        String interfaceName = RpcContext.getContext().getUrl().getServiceInterface();

        Builder builder = DefaultInvocation.builder()
                .setInterfaceName(interfaceName)
                .setMethodName(method);

        for (int i = 0; i < parameterTypes.length; i++) {
            builder.addParam(parameterTypes[i], args[i]);
        }

        DefaultInvocation invocation = builder.build();

        String request = serializer.serialize(invocation);
        Object result = requestSender.send(request);
        return responseParser.parse(result);
    }
}
