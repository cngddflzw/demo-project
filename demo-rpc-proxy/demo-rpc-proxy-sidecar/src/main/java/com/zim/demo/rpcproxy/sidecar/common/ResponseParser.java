package com.zim.demo.rpcproxy.sidecar.common;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface ResponseParser<From, To> {

    To parse(From input);
}
