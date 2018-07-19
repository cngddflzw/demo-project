package com.zim.demo.rpcproxy.sidecar.exception;

/**
 * proxy 与 异构语言 之间的各种请求异常
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class DelegateInvokeException extends RuntimeException {

    public DelegateInvokeException(Throwable cause) {
        super(cause);
    }
}
