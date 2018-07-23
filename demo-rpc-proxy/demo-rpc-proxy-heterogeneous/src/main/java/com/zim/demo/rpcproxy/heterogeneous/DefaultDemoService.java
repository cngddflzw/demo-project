package com.zim.demo.rpcproxy.heterogeneous;

import com.zim.demo.rpcproxy.api.ServiceInfo;
import com.zim.demo.rpcproxy.api.impl.DefaultServiceInfo;
import javax.annotation.Resource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 异构语言暴露出去的服务
 *
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Service
public class DefaultDemoService implements DemoService, InitializingBean, DisposableBean {

    @Resource
    private InvocationClient invocationClient;

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        invocationClient.export(createServiceInfo());
    }

    @Override
    public void destroy() throws Exception {
        invocationClient.unexport(createServiceInfo());
    }

    private ServiceInfo createServiceInfo() {
        return new DefaultServiceInfo()
                .setName("com.zim.demo.rpcproxy.heterogeneous.DemoService")
                .setVersion("1.0.0")
                .setGroup("arch");
    }
}
