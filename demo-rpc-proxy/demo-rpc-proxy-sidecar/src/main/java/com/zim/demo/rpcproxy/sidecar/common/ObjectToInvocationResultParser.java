package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocationResult;
import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class ObjectToInvocationResultParser implements
        ResponseParser<Object, InvocationResult> {

    @Override
    public InvocationResult parse(Object input) {
        DefaultInvocationResult result = new DefaultInvocationResult();
        result.setCode(0);
        result.setData(input);
        return result;
    }
}
