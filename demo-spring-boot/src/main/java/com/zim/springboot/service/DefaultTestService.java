package com.zim.springboot.service;

import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2019-10-24
 */
@Service
public class DefaultTestService implements TestService {

    @Override
    public String test() {
        return "test service";
    }
}
