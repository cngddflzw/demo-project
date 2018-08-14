package com.zim.demo.rpcproxy.sidecar.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zim.demo.rpcproxy.sidecar"})
public class SidecarBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(SidecarBootstrap.class);

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SidecarBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        LOGGER.info("Sidecar start up ... ");
        Thread.currentThread().join();
    }
}
