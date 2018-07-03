package com.zim.demo.dubbo.springboot.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zimliu
 */
@SpringBootApplication
public class DubboProviderSpringBootDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderSpringBootDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
