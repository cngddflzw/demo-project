package com.zim.demo.rpcproxy.api.impl;

import com.zim.demo.rpcproxy.api.InvocationResult;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class DefaultInvocationResult implements InvocationResult {

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public DefaultInvocationResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DefaultInvocationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public DefaultInvocationResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Object data() {
        return data;
    }
}
