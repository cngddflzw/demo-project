package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocationResult;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import java.util.Map;

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
