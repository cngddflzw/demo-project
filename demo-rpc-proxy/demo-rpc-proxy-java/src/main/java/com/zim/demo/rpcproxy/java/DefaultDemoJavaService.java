package com.zim.demo.rpcproxy.java;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}",
        group = "arch"
)
public class DefaultDemoJavaService implements DemoJavaService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
