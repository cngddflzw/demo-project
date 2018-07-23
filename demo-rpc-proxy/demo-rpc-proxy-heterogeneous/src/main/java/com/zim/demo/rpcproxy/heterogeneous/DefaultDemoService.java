package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 异构语言暴露出去的服务
 *
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Service
public class DefaultDemoService implements DemoService {

    @Resource
    private InvocationClient invocationClient;

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    private ServiceInfo createServiceInfo() {
        return new DefaultServiceInfo()
                .setServiceName("com.zim.demo.rpcproxy.heterogeneous.DemoService")
                .setVersion("1.0.0")
                .setGroup("arch");
    }
}
