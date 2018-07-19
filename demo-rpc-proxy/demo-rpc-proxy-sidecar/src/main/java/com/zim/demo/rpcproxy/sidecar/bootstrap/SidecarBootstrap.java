package com.zim.demo.rpcproxy.sidecar.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zim.demo.rpcproxy.sidecar"})
public class SidecarBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SidecarBootstrap.class, args);
    }
}
