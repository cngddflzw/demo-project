package com.zim.demo.rpcproxy.sidecar;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.junit.Test;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class Java2HeterogeneousInvocationTest {

    @Test
    public void testJava2HeterogeneousInvocation() throws Exception {

        ApplicationConfig applicationConfig = new ApplicationConfig("abc");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("localhost");
        registryConfig.setPort(2181);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface("com.zim.demo.rpcproxy.heterogeneous.DemoService");
        referenceConfig.setGroup("arch");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric(true);

        GenericService genericService = referenceConfig.get();
        Object result = genericService
                .$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"zim"});

        System.out.println(result);
    }
}
