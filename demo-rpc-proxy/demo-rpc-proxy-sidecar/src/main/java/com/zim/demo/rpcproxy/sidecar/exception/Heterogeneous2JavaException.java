package com.zim.demo.rpcproxy.sidecar.exception;

/**
 * 异构语言 调用 Java 服务 之间的各种调用异常
 *
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Heterogeneous2JavaException extends RuntimeException {

    public Heterogeneous2JavaException(String message) {
        super(message);
    }

    public Heterogeneous2JavaException(Throwable cause) {
        super(cause);
    }
}
