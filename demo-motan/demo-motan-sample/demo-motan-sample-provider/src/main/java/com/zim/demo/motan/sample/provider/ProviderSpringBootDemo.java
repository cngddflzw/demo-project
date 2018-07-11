package com.zim.demo.motan.sample.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhenwei.liu
 * @since 2018-07-11
 */
@SpringBootApplication
@ImportResource({"classpath*:motan-provider.xml"})
public class ProviderSpringBootDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderSpringBootDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
        ProviderInitializer.init();
    }
}
