package com.zim.demo.dubbo.springboot.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zim.demo.dubbo.api.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zimliu
 */
@RestController
public class DemoController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private DemoService demoService;

    @RequestMapping("/hello")
    public String hello() {
        return demoService.hello();
    }
}
