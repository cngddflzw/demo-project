package com.zim.demo.motan.sample.provider.impl;

import com.zim.demo.motan.api.DemoService;

/**
 * @author zhenwei.liu
 * @since 2018-07-11
 */
public class DemoServiceImpl implements DemoService {

    public String hello(String name) {
        return "hello motan " + name;
    }
}
