package com.zim.demo.motan.sample.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhenwei.liu
 * @since 2018-07-11
 */
@SpringBootApplication(scanBasePackages = {"com.zim.demo.motan.sample.consumer"})
@ImportResource({"classpath*:motan-consumer.xml"})
public class ConsumerSpringBootDemo {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSpringBootDemo.class, args);
    }
}
