package com.zim.springboot.tomcat.controller;

import com.zim.springboot.tomcat.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhenwei.liu
 * @since 2019-10-24
 */
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/test")
    public Object test() {
        return testService.test();
    }
}
