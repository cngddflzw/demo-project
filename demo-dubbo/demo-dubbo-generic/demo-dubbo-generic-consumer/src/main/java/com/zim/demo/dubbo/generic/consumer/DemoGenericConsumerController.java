package com.zim.demo.dubbo.generic.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@RestController
public class DemoGenericConsumerController {

    @Reference(
            version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private GenericService genericService;

    @RequestMapping("/demo")
    public Object demo(String name) {
//        return genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{name});

        return genericService.$invoke("sayHello", new String[]{"name"}, new Object[]{name});
    }
}
