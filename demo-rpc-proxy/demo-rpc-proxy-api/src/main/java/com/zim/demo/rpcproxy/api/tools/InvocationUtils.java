package com.zim.demo.rpcproxy.api.tools;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocationResult;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class InvocationUtils {

    public static InvocationResult createSuccessfulResult(Object data) {
        return new DefaultInvocationResult()
                .setCode(0)
                .setMessage("success")
                .setData(data);
    }

    public static InvocationResult createFailedResult(String message) {
        return new DefaultInvocationResult()
                .setCode(-1)
                .setMessage("fail")
                .setMessage(message);
    }
}
