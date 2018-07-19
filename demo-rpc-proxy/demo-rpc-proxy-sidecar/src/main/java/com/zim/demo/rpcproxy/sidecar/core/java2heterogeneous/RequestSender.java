package com.zim.demo.rpcproxy.sidecar.core.java2heterogeneous;

/**
 * <pre>
 * 发送请求到 delegate 机器, 一般来讲 ip, port, 调用方法,
 * 调用协议都是固定的, 所以 send() 方法只需要关注发送的参数即可
 * </pre>
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public interface RequestSender<Input, Output> {

    Output send(Input input);
}
