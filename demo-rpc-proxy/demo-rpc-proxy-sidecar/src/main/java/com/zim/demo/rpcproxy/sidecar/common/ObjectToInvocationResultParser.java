package com.zim.demo.rpcproxy.sidecar.common;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class ObjectToInvocationResultParser implements
        ResponseParser<Object, InvocationResult> {

    @Override
    public InvocationResult parse(Object input) {
        return InvocationUtils.createSuccessfulResult(input);
    }
}
