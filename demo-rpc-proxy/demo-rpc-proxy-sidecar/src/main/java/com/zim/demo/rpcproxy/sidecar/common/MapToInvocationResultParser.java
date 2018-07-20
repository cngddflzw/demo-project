package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.InvocationResult;
import com.zim.demo.rpcproxy.api.impl.DefaultInvocationResult;
import java.util.Map;

/**
 * @author zhenwei.liu
 * @since 2018-07-20
 */
public class MapToInvocationResultParser implements
        ResponseParser<Map<String, Object>, InvocationResult> {

    @Override
    public InvocationResult parse(Map<String, Object> input) {
        DefaultInvocationResult result = new DefaultInvocationResult();
        result.setCode(0);
        result.setData(JSON.toJSONString(input));
        return result;
    }
}
