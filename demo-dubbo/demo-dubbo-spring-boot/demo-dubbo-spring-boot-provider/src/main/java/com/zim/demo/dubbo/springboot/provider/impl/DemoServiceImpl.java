package com.zim.demo.dubbo.springboot.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zim.demo.dubbo.api.DemoService;

/**
 * @author zimliu
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DemoServiceImpl implements DemoService {

    public String hello() {
        return "Hello Dubbo Service";
    }
}
