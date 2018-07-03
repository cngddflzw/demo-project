package com.zim.demo.dubbo.rest.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zimliu
 */
@SpringBootApplication
public class RestDubboProviderSpringDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RestDubboProviderSpringDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
