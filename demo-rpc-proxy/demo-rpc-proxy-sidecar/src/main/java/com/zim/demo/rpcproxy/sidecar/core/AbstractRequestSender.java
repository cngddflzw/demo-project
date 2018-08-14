package com.zim.demo.rpcproxy.sidecar.core;

import com.zim.demo.rpcproxy.api.Invocation;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.sidecar.common.RequestParser;
import com.zim.demo.rpcproxy.sidecar.common.ResponseParser;
import com.zim.demo.rpcproxy.sidecar.exception.Java2HeterogeneousException;

/**
 * @author zhenwei.liu
 * @since 2018-08-13
 */
public abstract class AbstractRequestSender<Req, Resp> implements
        RequestSender<Invocation, InvocationResult> {

    @Override
    public InvocationResult send(Invocation invocation) {
        Req req = getRequestParser().parse(invocation);
        try {
            Resp resp = doSend(req);
            return getResponseParser().parse(resp);
        } catch (Throwable e) {
            throw new Java2HeterogeneousException(e);
        }
    }

    protected abstract Resp doSend(Req request) throws Exception;

    protected abstract RequestParser<Invocation, Req> getRequestParser();

    protected abstract ResponseParser<Resp, InvocationResult> getResponseParser();
}
