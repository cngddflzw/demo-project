package com.zim.demo.rpcproxy.sidecar.common;

import com.alibaba.fastjson.JSON;
import com.zim.demo.rpcproxy.api.InvocationResult;

/**
 * @author zhenwei.liu
 * @since 2018-07-19
 */
public class Json2InvocationResultParser implements ResponseParser<String, InvocationResult> {

    @Override
    public InvocationResult parse(String s) {
        return JSON.parseObject(s, InvocationResult.class);
    }
}
