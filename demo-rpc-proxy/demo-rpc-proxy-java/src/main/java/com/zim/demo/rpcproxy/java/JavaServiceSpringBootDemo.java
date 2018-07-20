package com.zim.demo.rpcproxy.java;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@SpringBootApplication
public class JavaServiceSpringBootDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(JavaServiceSpringBootDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
