package com.zim.demo.rpcproxy.sidecar.core.thrift;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.MethodInfo;
import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.api.ServiceManager;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocation;
import com.zim.demo.rpcproxy.api.thrift.ProxyService.Iface;
import com.zim.demo.rpcproxy.api.thrift.Request;
import com.zim.demo.rpcproxy.common.GsonHolder;
import com.zim.demo.rpcproxy.sidecar.common.ServiceKeyGenerator;
import com.zim.demo.rpcproxy.sidecar.core.Heterogeneous2JavaService;
import com.zim.demo.rpcproxy.sidecar.exception.Heterogeneous2JavaException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-08-14
 */
@Service
public class ThriftRequestHandler implements Iface {

    private static final Type LIST_TYPE = new TypeToken<List<String>>() {
    }.getType();

    @Resource
    private ServiceKeyGenerator serviceKeyGenerator;

    @Resource
    private ServiceManager<GenericService> serviceManager;

    @Resource
    private Heterogeneous2JavaService service;

    @Override
    public String Invoke(Request request) {
        Object result = service.invoke(adapt(request));
        return GsonHolder.gson().toJson(result);
    }

    private Invocation adapt(Request request) {

        String serviceKey = serviceKeyGenerator
                .generate(request.getClass_name(), request.getGroup(), request.getVersion());
        ServiceInfo serviceInfo = serviceManager.getReferenceInfo(serviceKey);
        if (serviceInfo == null) {
            throw new Heterogeneous2JavaException(
                    String.format("service not found %s", serviceKey));
        }

        Map<String, ? extends MethodInfo> methodInfoMap = serviceInfo.methodInfoMap();
        MethodInfo methodInfo = methodInfoMap.get(request.getFunc_name());

        DefaultInvocation inv = new DefaultInvocation();
        inv.setGroup(request.getGroup());
        inv.setVersion(request.getVersion());
        inv.setInterfaceName(request.getClass_name());
        inv.setMethodName(request.getFunc_name());
        inv.setParamTypes(methodInfo.paramTypeList());
        inv.setParamVals(parseParam(request.getParam(), methodInfo.paramTypeList()));

        return inv;
    }

    private List<Object> parseParam(String json, List<String> paramTypes) {
        List<Object> paramList = Lists.newArrayList();
        List<String> paramJsonList = GsonHolder.gson().fromJson(json, LIST_TYPE);
        for (int i = 0; i < paramJsonList.size(); i++) {
            String paramType = paramTypes.get(i);
            String param = paramJsonList.get(i);
            try {
                paramList.add(GsonHolder.gson().fromJson(param, Class.forName(paramType)));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return paramList;
    }
}
