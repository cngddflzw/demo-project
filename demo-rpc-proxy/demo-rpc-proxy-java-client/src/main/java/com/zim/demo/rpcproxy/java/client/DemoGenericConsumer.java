package com.zim.demo.rpcproxy.java.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.GenericService;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Service
public class DemoGenericConsumer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGenericConsumer.class);

    @Override
    public void afterPropertiesSet() {

        ApplicationConfig applicationConfig = new ApplicationConfig("abc");

        String serviceName = "DemoService";
        String serviceGroup = "arch";
        String serviceVersion = "1.0.0";

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("localhost");
        registryConfig.setPort(2181);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(serviceName);
        referenceConfig.setGroup(serviceGroup);
        referenceConfig.setVersion(serviceVersion);
        referenceConfig.setGeneric(true);

        GenericService genericService = referenceConfig.get();

        String method = "Test";
        String paramType = "com.zim.demo.rpcproxy.java.client.DemoGenericConsumer.Param";
        Param param = new Param().setName("Zim").setCode(1);

        LOGGER.info("invoking service {} group {} version {} method {} paramType {} param {}",
                serviceName, serviceGroup, serviceVersion, method, paramType, param);
        Object result = genericService
                .$invoke(method, new String[]{paramType}, new Object[]{param});
        LOGGER.info("invoke successfully, result: {}", result);
    }

    public static class Param implements Serializable {

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
}
