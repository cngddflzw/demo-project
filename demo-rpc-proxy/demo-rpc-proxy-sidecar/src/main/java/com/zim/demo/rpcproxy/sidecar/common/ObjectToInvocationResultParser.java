package com.zim.demo.rpcproxy.sidecar.common;

import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.tools.InvocationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
@Component
public class ObjectToInvocationResultParser implements
        ResponseParser<Object, InvocationResult> {

    @Override
    public InvocationResult parse(Object input) {
        return InvocationUtils.createSuccessfulResult(input);
    }
}
