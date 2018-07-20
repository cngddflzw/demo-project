package com.zim.demo.dubbo.generic.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@SpringBootApplication
public class GenericProviderSpringBootDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GenericProviderSpringBootDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
