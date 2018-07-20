package com.zim.demo.rpcproxy.api;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public interface InvocationResult {

    int code();

    String message();

    Object data();
}
