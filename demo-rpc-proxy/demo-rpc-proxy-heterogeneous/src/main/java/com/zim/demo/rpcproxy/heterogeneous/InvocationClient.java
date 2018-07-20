package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationService;
import com.zim.demo.rpcproxy.api.RegistryService;
import com.zim.demo.rpcproxy.api.ServiceInfo;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class InvocationClient implements RegistryService, InvocationService {



    @Override
    public void refer(ServiceInfo serviceInfo) {

    }

    @Override
    public Object invoke(Invocation invocation) {
        return null;
    }

    @Override
    public void register(ServiceInfo serviceInfo) {

    }

    @Override
    public void unregister(ServiceInfo serviceInfo) {

    }
}
