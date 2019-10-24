package com.zim.springboot.controller;

import com.zim.springboot.service.TestService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
