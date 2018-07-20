package com.zim.demo.rpcproxy.sidecar.exception;

/**
 * Java 服务 调用 异构语言 之间的各种请求异常
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Java2HeterogeneousException extends RuntimeException {

    public Java2HeterogeneousException(String message) {
        super(message);
    }

    public Java2HeterogeneousException(String message, Throwable cause) {
        super(message, cause);
    }

    public Java2HeterogeneousException(Throwable cause) {
        super(cause);
    }
}
