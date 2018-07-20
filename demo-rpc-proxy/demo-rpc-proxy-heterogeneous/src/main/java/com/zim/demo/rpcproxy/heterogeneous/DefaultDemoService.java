package com.zim.demo.rpcproxy.heterogeneous;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class DefaultDemoService implements DemoService {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
