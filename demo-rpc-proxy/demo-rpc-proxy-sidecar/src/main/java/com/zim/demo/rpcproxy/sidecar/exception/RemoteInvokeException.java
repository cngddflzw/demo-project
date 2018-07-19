package com.zim.demo.rpcproxy.sidecar.exception;

/**
 * Proxy 和其他 Java 服务之间的各种调用异常
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class RemoteInvokeException extends RuntimeException {

    public RemoteInvokeException(String message) {
        super(message);
    }

    public RemoteInvokeException(Throwable cause) {
        super(cause);
    }
}
