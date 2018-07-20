package com.zim.demo.dubbo.generic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DemoGenericService implements GenericService {

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {

        if ("sayHello".endsWith(method)) {
            return "hello " + parameterTypes[0] + ": " + args[0];
        }

        return null;
    }
}
