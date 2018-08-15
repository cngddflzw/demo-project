package com.zim.demo.rpcproxy.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@SpringBootApplication
public class JavaServiceSpringBootDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaServiceSpringBootDemo.class);

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(JavaServiceSpringBootDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);

        LOGGER.info("Java service start up ... ");
        Thread.currentThread().join();
    }
}
