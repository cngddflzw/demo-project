package com.zim.demo.rpcproxy.sidecar;

import static junit.framework.TestCase.assertEquals;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import java.io.Serializable;
import org.junit.Test;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class Java2HeterogeneousInvocationTest {

    private static class Param implements Serializable {

        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public Param setName(String name) {
            this.name = name;
            return this;
        }

        public int getCode() {
            return code;
        }

        public Param setCode(int code) {
            this.code = code;
            return this;
        }
    }

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
        referenceConfig.setInterface("DemoService");
        referenceConfig.setGroup("arch");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric(true);

        GenericService genericService = referenceConfig.get();
        String paramType = "com.zim.demo.rpcproxy.sidecar.Java2HeterogeneousInvocationTest.Param";

        Param param1 = new Param().setName("Zim").setCode(1);
        Object result1 = genericService
                .$invoke("Test", new String[]{paramType}, new Object[]{param1});
        System.out.println("Result1: " + result1);

        Param param2 = new Param().setName("Zack").setCode(2);
        Object result2 = genericService
                .$invoke("Test2", new String[]{paramType, paramType}, new Object[]{param1, param2});
        System.out.println("Result2: " + result2);
    }
}
